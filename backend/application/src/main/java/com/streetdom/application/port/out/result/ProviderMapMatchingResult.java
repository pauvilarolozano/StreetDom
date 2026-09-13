package com.streetdom.application.port.out.result;

import com.streetdom.domain.model.RoutingProvider;
import java.util.List;

public record ProviderMapMatchingResult(
        RoutingProvider provider,
        List<ProviderMatchedEdge> matchedEdges
) {}
