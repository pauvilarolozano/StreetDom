package com.streetdom.adapters.out.persistence.streetnetwork.mapper;

import com.streetdom.adapters.out.persistence.streetnetwork.entities.StreetSegmentEntity;
import com.streetdom.domain.model.streetnetwork.StreetSegment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class StreetSegmentMapper {

    private final GeometryMapper geometryMapper;

    public StreetSegmentEntity toEntity(StreetSegment streetSegment) {
        return StreetSegmentEntity.builder()
                .id(streetSegment.getId())
                .routingEdgeId(streetSegment.getRoutingEdgeId())
                .geometry(geometryMapper.toLineString(streetSegment.getGeometry()))
                .build();
    }

    public StreetSegment toDomain(StreetSegmentEntity entity) {
        return StreetSegment.builder()
                .id(entity.getId())
                .routingEdgeId(entity.getRoutingEdgeId())
                .geometry(geometryMapper.toLocations(entity.getGeometry()))
                .build();
    }

}
