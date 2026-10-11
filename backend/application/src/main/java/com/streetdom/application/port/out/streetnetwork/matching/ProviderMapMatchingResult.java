package com.streetdom.application.port.out.streetnetwork.matching;

import com.streetdom.domain.model.streetnetwork.RoutingProvider;
import java.util.List;

public record ProviderMapMatchingResult(
        RoutingProvider provider,
        List<ProviderMatchedEdge> matchedEdges
) {}
