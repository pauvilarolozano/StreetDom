package com.streetdom.adapters.out.persistence.streetnetwork.mapper;

import com.streetdom.adapters.out.persistence.streetnetwork.RoutingEdgeEntity;
import com.streetdom.domain.model.streetnetwork.RoutingEdge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoutingEdgeMapper {

    public RoutingEdgeEntity toEntity(RoutingEdge routingEdge) {
        return RoutingEdgeEntity.builder()
                .id(routingEdge.id())
                .provider(routingEdge.provider())
                .externalId(routingEdge.externalId())
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
