package com.streetdom.adapters.out.valhalla;

import com.streetdom.adapters.out.valhalla.model.Coordinate;
import com.streetdom.adapters.out.valhalla.model.ValhallaEdgeData;
import com.wdtinc.mapbox_vector_tile.VectorTile.Tile.Feature;
import com.wdtinc.mapbox_vector_tile.VectorTile.Tile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ValhallaEdgeReader {

    private static final int TILE_EXTENT = 4096;
    private final RestClient valhallaRestClient;
    private final ValhallaTileGeometryDecoder tileGeometryDecoder;

    public byte[] readTile(int zoom, int x, int y) {

        String json =
                "{\"tile\":{\"z\":%d,\"x\":%d,\"y\":%d},\"verbose\":true}"
                        .formatted(zoom, x, y);

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

                List<Coordinate> coordinates =
                        tileGeometryDecoder.decode(
                                feature,
                                zoom,
                                tileX,
                                tileY,
                                TILE_EXTENT
                        );

                edges.add(new ValhallaEdgeData(feature.getId(), coordinates));
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
}