package com.streetdom.domain.model;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StreetSegment {
    private UUID id;
    private RoutingEdge routingEdge;
    private List<Location> geometry;
}
