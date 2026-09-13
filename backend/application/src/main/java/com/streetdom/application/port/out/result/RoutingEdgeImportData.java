package com.streetdom.application.port.out.result;

import com.streetdom.domain.model.Location;
import com.streetdom.domain.model.RoutingEdge;
import com.streetdom.domain.model.RoutingProvider;
import lombok.Builder;
import java.util.List;

@Builder
public record RoutingEdgeImportData(
        RoutingProvider provider,
        String externalId,
        List<Location> geometry
) {}
