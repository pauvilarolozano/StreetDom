package com.streetdom.application.port.out.streetnetwork.loading;

import com.streetdom.domain.model.geography.Location;
import com.streetdom.domain.model.streetnetwork.RoutingProvider;
import lombok.Builder;
import java.util.List;

@Builder
public record RoutingEdgeImportData(
        RoutingProvider provider,
        String externalId,
        List<Location> geometry
) {}
