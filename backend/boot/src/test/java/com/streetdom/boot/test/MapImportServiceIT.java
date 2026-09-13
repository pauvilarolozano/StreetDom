package com.streetdom.boot.test;
import com.streetdom.adapters.out.persistence.jpa.RoutingEdgeJpaRepository;
import com.streetdom.adapters.out.persistence.jpa.StreetSegmentJpaRepository;
import com.streetdom.application.port.in.MapImportUseCase;
import com.streetdom.application.port.out.MapMatchingProvider;
import com.streetdom.application.service.MapImportService;
import com.streetdom.domain.model.GeographicBounds;
import com.streetdom.domain.model.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class MapImportServiceIT {


    @Autowired
    private MapMatchingProvider mapMatchingProvider;

    @Autowired
    private MapImportUseCase mapImportUseCase;

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

        mapImportUseCase.importArea(bounds);

        long routingEdges = routingEdgeJpaRepository.count();
        long streetSegments = streetSegmentJpaRepository.count();

        System.out.println("Routing edges: " + routingEdges);
        System.out.println("Street segments: " + streetSegments);

        assertTrue(routingEdges > 0);
        assertTrue(streetSegments > 0);
    }
}