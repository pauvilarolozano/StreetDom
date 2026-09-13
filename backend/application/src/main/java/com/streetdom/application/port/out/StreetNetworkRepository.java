package com.streetdom.application.port.out;

import com.streetdom.domain.model.Location;
import com.streetdom.domain.model.RoutingEdge;
import com.streetdom.domain.model.RoutingProvider;
import com.streetdom.domain.model.StreetSegment;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StreetNetworkRepository {

    void save(
            List<RoutingEdge> routingEdges,
            List<StreetSegment> streetSegments
    );

    Optional<RoutingEdge> findRoutingEdge(
            RoutingProvider provider,
            String externalId
    );

    List<StreetSegment> findStreetSegmentsWithinDistance(
            List<List<Location>> edgeGeometries
    );

    Set<String> findExistingExternalIds(
            RoutingProvider provider,
            Collection<String> externalIds
    );

}
