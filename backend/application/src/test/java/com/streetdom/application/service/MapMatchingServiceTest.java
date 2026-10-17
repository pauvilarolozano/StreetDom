package com.streetdom.application.service;

import com.streetdom.application.streetnetwork.matching.MapMatchingService;
import com.streetdom.application.port.out.streetnetwork.matching.MapMatchingProvider;
import com.streetdom.application.port.out.streetnetwork.StreetNetworkRepository;
import com.streetdom.application.port.out.streetnetwork.matching.ProviderMapMatchingResult;
import com.streetdom.application.port.out.streetnetwork.matching.ProviderMatchedEdge;
import com.streetdom.domain.model.geography.Location;
import com.streetdom.domain.model.streetnetwork.RoutingProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MapMatchingServiceTest {

    @Mock
    private MapMatchingProvider mapMatchingProvider;

    @Mock
    private StreetNetworkRepository streetNetworkRepository;

    @InjectMocks
    private MapMatchingService mapMatchingService;

    @Test
    void shouldReturnMatchedStreetSegmentIds() {

        List<Location> inputLocations = List.of(
                new Location(41.37958, 2.15517),
                new Location(41.37969, 2.15532)
        );

        List<Location> edgeGeometry = List.of(
                new Location(41.37958, 2.15517),
                new Location(41.37964, 2.15524),
                new Location(41.37969, 2.15532)
        );

        UUID streetSegmentId = UUID.randomUUID();

        ProviderMatchedEdge matchedEdge =
                new ProviderMatchedEdge(
                        "233773751144",
                        edgeGeometry
                );

        ProviderMapMatchingResult providerResult =
                new ProviderMapMatchingResult(
                        RoutingProvider.VALHALLA,
                        List.of(matchedEdge)
                );

        when(mapMatchingProvider.match(inputLocations))
                .thenReturn(providerResult);

        when(streetNetworkRepository.findStreetSegmentIdsNearEdges(
                List.of(edgeGeometry)
        )).thenReturn(Set.of(streetSegmentId));

        Set<UUID> result =
                mapMatchingService.match(inputLocations);

        assertEquals(Set.of(streetSegmentId), result);
    }
}
