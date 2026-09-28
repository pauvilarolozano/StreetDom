package com.streetdom.domain.model.geography;

import lombok.Builder;
import lombok.Value;
import java.util.UUID;

@Builder
@Value
public class RoutingEdge {
    UUID id;
    RoutingProvider provider;
    String externalId;
}
