package com.streetdom.application.port.out.streetnetwork;

import com.streetdom.domain.model.geography.GeographicArea;
import com.streetdom.domain.model.geography.Location;
import com.streetdom.domain.model.streetnetwork.RoutingEdge;
import com.streetdom.domain.model.streetnetwork.RoutingProvider;
import com.streetdom.domain.model.streetnetwork.StreetSegment;

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

    List<StreetSegment> findStreetSegmentsNearEdges(
            List<List<Location>> edgeGeometries
    );

    Set<String> findExistingRoutingEdgeIds(
            RoutingProvider provider,
            Collection<String> externalIds
    );

    List<StreetSegment> findStreetSegmentsInArea(
            GeographicArea area
    );



}
