package com.streetdom.application.streetnetwork.loading;

import com.streetdom.application.port.out.streetnetwork.loading.StreetNetworkDataSource;
import com.streetdom.application.port.out.streetnetwork.StreetNetworkRepository;
import com.streetdom.application.port.out.streetnetwork.loading.RoutingEdgeImportData;
import com.streetdom.domain.model.geography.*;
import com.streetdom.domain.model.streetnetwork.RoutingEdge;
import com.streetdom.domain.model.streetnetwork.RoutingProvider;
import com.streetdom.domain.model.streetnetwork.StreetSegment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StreetNetworkImportService implements StreetNetworkImportUseCase {

    private static final int BATCH_SIZE = 1000;

    private final StreetNetworkDataSource streetNetworkDataSource;
    private final SegmentLengthSplitter segmentLengthSplitter;
    private final StreetNetworkRepository streetNetworkRepository;

    @Override
    public void importArea(GeographicBounds bounds) {

        //TODO: podria mejorar el batching importando por partes y no todo de golpe que queda en memoria hasta el save
        List<RoutingEdgeImportData> importedEdges = streetNetworkDataSource.readEdges(bounds);
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
                                    .routingEdgeId(routingEdge.id())
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

        return streetNetworkRepository.findExistingRoutingEdgeIds(
                provider,
                externalIds
        );
    }



}
