package com.streetdom.adapters.out.persistence.mapper;

import com.streetdom.adapters.out.persistence.entity.RoutingEdgeEntity;
import com.streetdom.domain.model.RoutingEdge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoutingEdgeMapper {

    public RoutingEdgeEntity toEntity(RoutingEdge routingEdge) {
        return RoutingEdgeEntity.builder()
                .id(routingEdge.getId())
                .provider(routingEdge.getProvider())
                .externalId(routingEdge.getExternalId())
                .build();
    }

    public RoutingEdge toDomain(RoutingEdgeEntity entity) {
        return RoutingEdge.builder()
                .id(entity.getId())
                .provider(entity.getProvider())
                .externalId(entity.getExternalId())
                .build();
    }
}
