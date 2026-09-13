package com.streetdom.application.port.out.result;

import com.streetdom.domain.model.Location;
import java.util.List;

public record ProviderMatchedEdge(
        String externalId,
        List<Location> edgeGeometry
) {}
