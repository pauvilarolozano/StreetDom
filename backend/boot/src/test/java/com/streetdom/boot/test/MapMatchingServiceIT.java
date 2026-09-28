package com.streetdom.boot.test;

import com.streetdom.application.service.MapMatchingService;
import com.streetdom.domain.model.geography.Location;
import com.streetdom.domain.model.geography.StreetSegment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class MapMatchingServiceIT {

    @Autowired
    private MapMatchingService mapMatchingService;

    @Test
    void shouldMatchRouteThroughBarcelonaWithTurnsAndGpsNoise() {

        List<Location> locations = List.of(
                new Location(41.40082, 2.17058),
                new Location(41.40119, 2.17114),
                new Location(41.40162, 2.17176),
                new Location(41.40207, 2.17238),
                new Location(41.40242, 2.17293),
                new Location(41.40283, 2.17343),
                new Location(41.40320, 2.17393),
                new Location(41.40361, 2.17425),
                new Location(41.40401, 2.17375),
                new Location(41.40443, 2.17324),
                new Location(41.40486, 2.17275)
        );

        List<StreetSegment> result =
                mapMatchingService.match(locations);

        assertNotNull(result);
        assertFalse(result.isEmpty());

        assertTrue(
                result.size() > 3,
                "Expected several matched street segments"
        );

        Set<UUID> uniqueIds = new HashSet<>();

        for (StreetSegment segment : result) {

            assertNotNull(segment);
            assertNotNull(segment.getId());
            assertNotNull(segment.getRoutingEdge());
            assertNotNull(segment.getGeometry());
            assertFalse(segment.getGeometry().isEmpty());

            assertTrue(
                    uniqueIds.add(segment.getId()),
                    "Duplicate StreetSegment found: "
                            + segment.getId()
            );
        }
    }

    @Test
    void shouldMatchShortRouteOnSingleStreet() {

        List<Location> locations = List.of(
                new Location(41.40082, 2.17058),
                new Location(41.40100, 2.17030),
                new Location(41.40118, 2.17004)
        );

        List<StreetSegment> result =
                mapMatchingService.match(locations);

        assertNotNull(result);
        assertFalse(result.isEmpty());

        System.out.println(
                "Short route matched StreetSegments: "
                        + result.size()
        );

        for (StreetSegment segment : result) {
            System.out.println(
                    "Segment "
                            + segment.getId()
                            + " -> "
                            + segment.getGeometry()
            );

            assertNotNull(segment.getId());
            assertNotNull(segment.getRoutingEdge());
            assertNotNull(segment.getGeometry());
            assertFalse(segment.getGeometry().isEmpty());
        }

        Set<UUID> uniqueIds =
                result.stream()
                        .map(StreetSegment::getId)
                        .collect(Collectors.toSet());

        assertEquals(
                result.size(),
                uniqueIds.size(),
                "There should be no duplicate StreetSegments"
        );
    }
}