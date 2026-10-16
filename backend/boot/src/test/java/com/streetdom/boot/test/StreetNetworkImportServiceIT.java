package com.streetdom.boot.test;
import com.streetdom.adapters.out.persistence.streetnetwork.jpa.RoutingEdgeJpaRepository;
import com.streetdom.adapters.out.persistence.streetnetwork.jpa.StreetSegmentJpaRepository;
import com.streetdom.application.streetnetwork.loading.StreetNetworkImportUseCase;
import com.streetdom.application.port.out.streetnetwork.matching.MapMatchingProvider;
import com.streetdom.domain.model.geography.GeographicBounds;
import com.streetdom.domain.model.geography.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class StreetNetworkImportServiceIT {


    @Autowired
    private MapMatchingProvider mapMatchingProvider;

    @Autowired
    private StreetNetworkImportUseCase streetNetworkImportUseCase;

    @Autowired
    private RoutingEdgeJpaRepository routingEdgeJpaRepository;

    @Autowired
    private StreetSegmentJpaRepository streetSegmentJpaRepository;

    @Test
    void shouldMatchTrace() {

        List<Location> locations = List.of(
                new Location(
                        41.37958599441352,
                        2.155176401138306
                ),
                new Location(
                        41.37964000000000,
                        2.155250000000000
                ),
                new Location(
                        41.37969467399516,
                        2.15532124042511
                )
        );

        mapMatchingProvider.match(locations);

    }


    @Test
    void shouldImportAreaWithoutDuplicatingRoutingEdges() {

        GeographicBounds bounds =
                new GeographicBounds(
                        new Location(41.37, 2.16),
                        new Location(41.39, 2.18)
                );

        streetNetworkImportUseCase.importArea(bounds);

        long routingEdges = routingEdgeJpaRepository.count();
        long streetSegments = streetSegmentJpaRepository.count();

        System.out.println("Routing edges: " + routingEdges);
        System.out.println("Street segments: " + streetSegments);

        assertTrue(routingEdges > 0);
        assertTrue(streetSegments > 0);
    }
}