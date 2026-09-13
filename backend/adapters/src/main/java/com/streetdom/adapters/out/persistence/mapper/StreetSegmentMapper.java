package com.streetdom.adapters.out.persistence.mapper;

import com.streetdom.adapters.out.persistence.entity.RoutingEdgeEntity;
import com.streetdom.adapters.out.persistence.entity.StreetSegmentEntity;
import com.streetdom.domain.model.RoutingEdge;
import com.streetdom.domain.model.StreetSegment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreetSegmentMapper {

    private final GeometryMapper geometryMapper;

    public StreetSegmentEntity toEntity(StreetSegment streetSegment, RoutingEdgeEntity routingEdgeEntity) {
        return StreetSegmentEntity.builder()
                .id(streetSegment.getId())
                .routingEdge(routingEdgeEntity)
                .geometry(geometryMapper.toLineString(streetSegment.getGeometry()))
                .build();
    }

    public StreetSegment toDomain(StreetSegmentEntity entity, RoutingEdge routingEdge) {
        return StreetSegment.builder()
                .id(entity.getId())
                .routingEdge(routingEdge)
                .geometry(geometryMapper.toLocations(entity.getGeometry()))
                .build();
    }

}
