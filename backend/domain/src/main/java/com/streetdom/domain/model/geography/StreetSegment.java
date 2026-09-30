package com.streetdom.domain.model.geography;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
public class StreetSegment {

    private final UUID id;
    private final RoutingEdge routingEdge;
    private final List<Location> geometry;

    @Builder
    private StreetSegment(
            UUID id,
            RoutingEdge routingEdge,
            List<Location> geometry
    ) {
        this.id = Objects.requireNonNull(id);
        this.routingEdge = Objects.requireNonNull(routingEdge);
        this.geometry = List.copyOf(Objects.requireNonNull(geometry));

        if (geometry.size() < 2) {
            throw new IllegalArgumentException("Street segment geometry must contain at least two locations");
        }
    }
}