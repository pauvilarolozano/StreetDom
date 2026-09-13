package com.streetdom.application.service;

import com.streetdom.application.port.in.MapImportUseCase;
import com.streetdom.application.port.out.MapDataSource;
import com.streetdom.application.port.out.StreetNetworkRepository;
import com.streetdom.application.port.out.result.RoutingEdgeImportData;
import com.streetdom.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MapImportService implements MapImportUseCase {

    private static final int BATCH_SIZE = 1000;

    private final MapDataSource mapDataSource;
    private final SegmentLengthSplitter segmentLengthSplitter;
    private final StreetNetworkRepository streetNetworkRepository;

    @Override
    public void importArea(GeographicBounds bounds) {

        List<RoutingEdgeImportData> importedEdges = mapDataSource.readEdges(bounds);
        int importedEdgesSize = importedEdges.size();

        if (importedEdgesSize == 0) {
            return;
        }

        RoutingProvider provider = importedEdges.getFirst().provider();

        for (int i = 0; i < importedEdgesSize; i += BATCH_SIZE) {

            int end = Math.min(i + BATCH_SIZE, importedEdgesSize);

            List<RoutingEdgeImportData> importedEdgesBatch = importedEdges.subList(i, end);
            Set<String> existingExternalIds = findExistingExternalIds(
                    provider,
                    importedEdgesBatch
            );

            List<RoutingEdge> routingEdges = new ArrayList<>();
            List<StreetSegment> streetSegments = new ArrayList<>();

            for (RoutingEdgeImportData importedEdge: importedEdgesBatch) {

                if (existingExternalIds.contains(importedEdge.externalId())) {
                    continue;
                }

                RoutingEdge routingEdge =
                        RoutingEdge.builder()
                                .id(UUID.randomUUID())
                                .provider(importedEdge.provider())
                                .externalId(importedEdge.externalId())
                                .build();

                routingEdges.add(routingEdge);

                List<List<Location>> edgeSegmentGeometries = segmentLengthSplitter.split(importedEdge.geometry());

                for (List<Location> geometry : edgeSegmentGeometries) {
                    StreetSegment streetSegment =
                            StreetSegment.builder()
                                    .id(UUID.randomUUID())
                                    .routingEdge(routingEdge)
                                    .geometry(geometry)
                                    .build();

                    streetSegments.add(streetSegment);
                }

            }

            if (!routingEdges.isEmpty()) {
                streetNetworkRepository.save(
                        routingEdges,
                        streetSegments
                );
            }
        }
    }

    private Set<String> findExistingExternalIds(
            RoutingProvider provider,
            List<RoutingEdgeImportData> importedEdges
    ) {

        List<String> externalIds =
                importedEdges.stream()
                        .map(RoutingEdgeImportData::externalId)
                        .distinct()
                        .toList();

        return streetNetworkRepository.findExistingExternalIds(
                provider,
                externalIds
        );
    }



}
