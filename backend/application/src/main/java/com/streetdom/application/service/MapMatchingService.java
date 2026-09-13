package com.streetdom.application.service;

import com.streetdom.application.command.MapMatchingCommand;
import com.streetdom.application.port.in.MapMatchingUseCase;
import com.streetdom.application.port.in.result.MapMatchingResult;
import com.streetdom.application.port.out.MapMatchingProvider;
import com.streetdom.application.port.out.StreetNetworkRepository;
import com.streetdom.application.port.out.result.ProviderMapMatchingResult;
import com.streetdom.application.port.out.result.ProviderMatchedEdge;
import com.streetdom.domain.model.Location;
import com.streetdom.domain.model.StreetSegment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapMatchingService implements MapMatchingUseCase {

    private final MapMatchingProvider mapMatchingProvider;
    private final StreetNetworkRepository streetNetworkRepository;

    @Override
    public MapMatchingResult match(MapMatchingCommand command) {

        ProviderMapMatchingResult matchingResult = mapMatchingProvider.match(command.locations());

        List<List<Location>> matchedGeometries =
                matchingResult.matchedEdges()
                        .stream()
                        .map(ProviderMatchedEdge::edgeGeometry)
                        .toList();

        List<StreetSegment> streetSegments =
                streetNetworkRepository
                        .findStreetSegmentsWithinDistance(
                                matchedGeometries
                        );

        return new MapMatchingResult(streetSegments);
    }
}