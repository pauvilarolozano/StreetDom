package com.streetdom.application.service;

import com.streetdom.application.command.MapMatchingCommand;
import com.streetdom.application.port.in.result.MapMatchingResult;
import com.streetdom.application.port.out.MapMatchingProvider;
import com.streetdom.application.port.out.StreetNetworkRepository;
import com.streetdom.application.port.out.result.ProviderMapMatchingResult;
import com.streetdom.application.port.out.result.ProviderMatchedEdge;
import com.streetdom.domain.model.Location;
import com.streetdom.domain.model.RoutingEdge;
import com.streetdom.domain.model.RoutingProvider;
import com.streetdom.domain.model.StreetSegment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
    void shouldReturnMatchedStreetSegments() {

        List<Location> inputLocations = List.of(
                new Location(41.37958, 2.15517),
                new Location(41.37969, 2.15532)
        );

        List<Location> edgeGeometry = List.of(
                new Location(41.37958, 2.15517),
                new Location(41.37964, 2.15524),
                new Location(41.37969, 2.15532)
        );

        RoutingEdge routingEdge = RoutingEdge.builder()
                .id(UUID.randomUUID())
                .provider(RoutingProvider.VALHALLA)
                .externalId("233773751144")
                .build();

        StreetSegment streetSegment = StreetSegment.builder()
                .id(UUID.randomUUID())
                .routingEdge(routingEdge)
                .geometry(edgeGeometry)
                .build();

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

        when(streetNetworkRepository.findStreetSegmentsWithinDistance(
                List.of(edgeGeometry)
        )).thenReturn(List.of(streetSegment));

        MapMatchingResult result =
                mapMatchingService.match(
                        new MapMatchingCommand(inputLocations)
                );

        assertEquals(1, result.streetSegments().size());

        assertEquals(
                streetSegment.getId(),
                result.streetSegments().getFirst().getId()
        );
    }
}