package com.streetdom.adapters.out.valhalla;

import com.streetdom.adapters.out.valhalla.model.Coordinate;
import com.streetdom.adapters.out.valhalla.model.ValhallaEdgeData;
import com.wdtinc.mapbox_vector_tile.VectorTile.Tile.Feature;
import com.wdtinc.mapbox_vector_tile.VectorTile.Tile;
import com.wdtinc.mapbox_vector_tile.VectorTile.Tile.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValhallaEdgeReader {

    private static final int TILE_EXTENT = 4096;
    // Valhalla baldr/graphconstants.h: pedestrian access and ordinary motor traffic.
    // Emergency access alone does not turn a pedestrian way into a road.
    private static final long PEDESTRIAN_ACCESS = 2;
    private static final long MOTOR_VEHICLE_ACCESS = 1 | 8 | 32 | 64 | 128 | 512 | 1024;
    // Track, cycleway, mountain-bike trail, sidewalk, footway, steps, path,
    // pedestrian street, bridleway and pedestrian crossing; access is checked separately.
    private static final Set<Long> RUNNABLE_USES = Set.of(3L, 20L, 21L, 24L, 25L, 26L, 27L, 28L, 29L, 32L);
    private final RestClient valhallaRestClient;
    private final ValhallaTileGeometryDecoder tileGeometryDecoder;

    public byte[] readTile(int zoom, int x, int y) {

        String json = """
                {"tile":{"z":%d,"x":%d,"y":%d},
                 "filters":{"action":"include","attributes":[
                   "edge.use","edge.access_forward","edge.access_backward"
                 ]}}
                """.formatted(zoom, x, y);

        return valhallaRestClient.get()
                .uri("/tile?json={json}", json)
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        (request, response) -> {
                            String errorBody = new String(response.getBody().readAllBytes(),
                                    StandardCharsets.UTF_8
                            );

                            throw new RuntimeException(
                                    "Valhalla returned status "
                                            + response.getStatusCode().value()
                                            + ": "
                                            + errorBody
                            );
                        }
                )
                .body(byte[].class);
    }

    public List<ValhallaEdgeData> readEdges(int zoom, int tileX, int tileY) {

        try {
            byte[] tileBytes = readTile(zoom, tileX, tileY);

            if (tileBytes == null || tileBytes.length == 0) {
                return List.of();
            }

            Tile tile = Tile.parseFrom(tileBytes);

            Tile.Layer edgesLayer =
                    tile.getLayersList()
                            .stream()
                            .filter(layer -> layer.getName().equals("edges"))
                            .findFirst()
                            .orElse(null);

            if (edgesLayer == null) {
                return List.of();
            }

            List<ValhallaEdgeData> edges = new ArrayList<>();

            for (Feature feature : edgesLayer.getFeaturesList()) {

                if (!isRunnable(edgesLayer, feature)) {
                    continue;
                }

                List<Coordinate> coordinates =
                        tileGeometryDecoder.decode(
                                feature,
                                zoom,
                                tileX,
                                tileY,
                                edgesLayer.hasExtent() ? edgesLayer.getExtent() : TILE_EXTENT
                        );

                if (coordinates.size() >= 2) {
                    edges.add(new ValhallaEdgeData(feature.getId(), coordinates));
                }
            }

            return edges;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Error reading Valhalla edges for tile "
                            + zoom + "/" + tileX + "/" + tileY,
                    e
            );
        }
    }

    private boolean isRunnable(Tile.Layer layer, Feature feature) {
        Long use = null;
        Long forwardAccess = null;
        Long backwardAccess = null;

        for (int i = 0; i < feature.getTagsCount(); i += 2) {
            String key = layer.getKeys(feature.getTags(i));
            Value value = layer.getValues(feature.getTags(i + 1));
            switch (key) {
                case "use" -> use = numericValue(value);
                case "access:fwd", "access:forward" -> forwardAccess = numericValue(value);
                case "access:bwd", "access:backward" -> backwardAccess = numericValue(value);
                default -> { }
            }
        }

        if (use == null || (forwardAccess == null && backwardAccess == null)) {
            throw new IllegalStateException("Valhalla tile is missing use/access attributes required for the runnable-way filter. "
                    + "Available attributes: " + layer.getKeysList());
        }

        long access = (forwardAccess == null ? 0 : forwardAccess)
                | (backwardAccess == null ? 0 : backwardAccess);
        return RUNNABLE_USES.contains(use)
                && (access & PEDESTRIAN_ACCESS) != 0
                && (access & MOTOR_VEHICLE_ACCESS) == 0;
    }

    private long numericValue(Value value) {
        if (value.hasUintValue()) {
            return value.getUintValue();
        }
        if (value.hasIntValue()) {
            return value.getIntValue();
        }
        if (value.hasSintValue()) {
            return value.getSintValue();
        }
        throw new IllegalArgumentException("Expected a numeric Valhalla use/access attribute");
    }
}
