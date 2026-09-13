package com.streetdom.adapters.out.persistence;

import com.streetdom.adapters.out.persistence.entity.RoutingEdgeEntity;
import com.streetdom.adapters.out.persistence.entity.StreetSegmentEntity;
import com.streetdom.adapters.out.persistence.jpa.RoutingEdgeJpaRepository;
import com.streetdom.adapters.out.persistence.jpa.StreetSegmentJpaRepository;
import com.streetdom.adapters.out.persistence.mapper.GeometryMapper;
import com.streetdom.adapters.out.persistence.mapper.RoutingEdgeMapper;
import com.streetdom.adapters.out.persistence.mapper.StreetSegmentMapper;
import com.streetdom.application.port.out.StreetNetworkRepository;
import com.streetdom.domain.model.Location;
import com.streetdom.domain.model.RoutingEdge;
import com.streetdom.domain.model.RoutingProvider;
import com.streetdom.domain.model.StreetSegment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.MultiLineString;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@RequiredArgsConstructor
public class StreetNetworkPersistenceAdapter implements StreetNetworkRepository {

    private final RoutingEdgeJpaRepository routingEdgeJpaRepository;
    private final StreetSegmentJpaRepository streetSegmentJpaRepository;
    private final RoutingEdgeMapper routingEdgeMapper;
    private final StreetSegmentMapper streetSegmentMapper;
    private final GeometryMapper geometryMapper;

    private static final double MATCH_TOLERANCE_METERS = 1.0;
    private static final double MINIMUM_OVERLAP = 0.8;

    @Override
    @Transactional
    public void save(List<RoutingEdge> routingEdges, List<StreetSegment> streetSegments) {

        Map<UUID, RoutingEdgeEntity> routingEdgesEntitiesById = new HashMap<>();

        for (RoutingEdge routingEdge : routingEdges) {
            RoutingEdgeEntity entity = routingEdgeMapper.toEntity(routingEdge);
            routingEdgesEntitiesById.put(routingEdge.getId(), entity);
        }

        routingEdgeJpaRepository.saveAll(routingEdgesEntitiesById.values());

        List<StreetSegmentEntity> streetSegmentEntities =
                streetSegments.stream()
                        .map(streetSegment ->
                             streetSegmentMapper.toEntity(
                                    streetSegment,
                                    routingEdgesEntitiesById.get(streetSegment.getRoutingEdge().getId())
                             )
                        ).toList();

        streetSegmentJpaRepository.saveAll(streetSegmentEntities);
    }

    @Override
    public Optional<RoutingEdge> findRoutingEdge(RoutingProvider provider, String externalId) {
        return routingEdgeJpaRepository.findByProviderAndExternalId(provider, externalId)
                .map(routingEdgeMapper::toDomain);
    }

    @Override
    @Transactional
    public List<StreetSegment> findStreetSegmentsWithinDistance(List<List<Location>> edgeGeometries) {

        MultiLineString matchedGeometry =
                geometryMapper.toMultiLineString(edgeGeometries);

        String geometryWkt = matchedGeometry.toText();

        return streetSegmentJpaRepository
                .findWithinDistanceOfGeometry(
                        geometryWkt,
                        MATCH_TOLERANCE_METERS,
                        MINIMUM_OVERLAP
                )
                .stream()
                .map(entity -> {
                        RoutingEdge routingEdge =
                                routingEdgeMapper.toDomain(
                                        entity.getRoutingEdge()
                                );

                        return streetSegmentMapper.toDomain(
                                entity,
                                routingEdge
                        );
                })
                .toList();
    }

    @Override
    public Set<String> findExistingExternalIds(RoutingProvider provider, Collection<String> externalIds) {
        return routingEdgeJpaRepository.findExistingExternalIds(provider, externalIds);
    }

}








