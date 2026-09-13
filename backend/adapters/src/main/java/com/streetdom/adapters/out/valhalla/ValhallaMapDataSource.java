package com.streetdom.adapters.out.valhalla;

import com.streetdom.adapters.out.valhalla.model.ValhallaEdgeData;
import com.streetdom.adapters.out.valhalla.mapper.ValhallaRoutingEdgeImportMapper;
import com.streetdom.application.port.out.MapDataSource;
import com.streetdom.application.port.out.result.RoutingEdgeImportData;
import com.streetdom.domain.model.GeographicBounds;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ValhallaMapDataSource implements MapDataSource {

    private static final int IMPORT_ZOOM = 14;

    private final ValhallaEdgeReader edgeReader;
    private final ValhallaRoutingEdgeImportMapper routingEdgeImportMapper;

    @Override
    public List<RoutingEdgeImportData> readEdges(GeographicBounds bounds) {

        int minTileX = longitudeToTileX(bounds.southWest().longitude());
        int maxTileX = longitudeToTileX(bounds.northEast().longitude());

        int minTileY = latitudeToTileY(bounds.northEast().latitude());
        int maxTileY = latitudeToTileY(bounds.southWest().latitude());

        Map<Long, ValhallaEdgeData> uniqueEdges = new LinkedHashMap<>();

        for (int tileX = minTileX; tileX <= maxTileX; tileX++) {
            for (int tileY = minTileY; tileY <= maxTileY; tileY++) {

                List<ValhallaEdgeData> edges = edgeReader.readEdges(IMPORT_ZOOM, tileX, tileY);


                for (ValhallaEdgeData edge : edges) {
                    uniqueEdges.putIfAbsent(
                            edge.id(),
                            edge
                    );
                }
            }
        }

        return uniqueEdges.values()
                .stream()
                .map(routingEdgeImportMapper::toImportData)
                .toList();
    }


    private int longitudeToTileX(double longitude) {
        double tilesPerAxis = Math.pow(2, ValhallaMapDataSource.IMPORT_ZOOM);
        return (int) Math.floor((longitude + 180.0) / 360.0 * tilesPerAxis);
    }

    private int latitudeToTileY(double latitude) {
        double latitudeRadians = Math.toRadians(latitude);
        double tilesPerAxis = Math.pow(2, ValhallaMapDataSource.IMPORT_ZOOM);
        double mercatorY = Math.log(Math.tan(latitudeRadians) + 1.0 / Math.cos(latitudeRadians));

        return (int) Math.floor((1.0 - mercatorY / Math.PI) / 2.0 * tilesPerAxis);
    }
}
