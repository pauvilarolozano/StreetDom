package com.streetdom.adapters.out.persistence.streetnetwork;

import com.streetdom.adapters.out.persistence.streetnetwork.entities.RoutingEdgeEntity;
import com.streetdom.adapters.out.persistence.streetnetwork.entities.StreetSegmentEntity;
import com.streetdom.adapters.out.persistence.streetnetwork.jpa.RoutingEdgeJpaRepository;
import com.streetdom.adapters.out.persistence.streetnetwork.jpa.StreetSegmentJpaRepository;
import com.streetdom.adapters.out.persistence.streetnetwork.mapper.GeometryMapper;
import com.streetdom.adapters.out.persistence.streetnetwork.mapper.RoutingEdgeMapper;
import com.streetdom.adapters.out.persistence.streetnetwork.mapper.StreetSegmentMapper;
import com.streetdom.application.port.out.streetnetwork.StreetNetworkRepository;
import com.streetdom.domain.model.geography.GeographicArea;
import com.streetdom.domain.model.geography.Location;
import com.streetdom.domain.model.streetnetwork.RoutingEdge;
import com.streetdom.domain.model.streetnetwork.RoutingProvider;
import com.streetdom.domain.model.streetnetwork.StreetSegment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Geometry;
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
            routingEdgesEntitiesById.put(routingEdge.id(), entity);
        }

        routingEdgeJpaRepository.saveAll(routingEdgesEntitiesById.values());

        List<StreetSegmentEntity> streetSegmentEntities =
                streetSegments.stream()
                        .map(streetSegmentMapper::toEntity)
                        .toList();

        streetSegmentJpaRepository.saveAll(streetSegmentEntities);
    }

    @Override
    public Optional<RoutingEdge> findRoutingEdge(RoutingProvider provider, String externalId) {
        return routingEdgeJpaRepository.findByProviderAndExternalId(provider, externalId)
                .map(routingEdgeMapper::toDomain);
    }

    @Override
    @Transactional
    public List<StreetSegment> findStreetSegmentsNearEdges(List<List<Location>> edgeGeometries) {

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
                .map(streetSegmentMapper::toDomain)
                .toList();
    }

    @Override
    public Set<String> findExistingRoutingEdgeIds(RoutingProvider provider, Collection<String> externalIds) {
        return routingEdgeJpaRepository.findExistingExternalIds(provider, externalIds);
    }

    @Override
    public List<StreetSegment> findStreetSegmentsInArea(GeographicArea area) {

        Geometry areaGeometry = geometryMapper.toAreaGeometry(area.polygons());
        String areaWkt = areaGeometry.toText();

        return streetSegmentJpaRepository
                .findIntersectingArea(areaWkt)
                .stream()
                .map(streetSegmentMapper::toDomain)
                .toList();
    }
}








