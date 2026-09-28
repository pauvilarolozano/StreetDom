package com.streetdom.application.service;

import com.streetdom.application.port.out.MapMatchingProvider;
import com.streetdom.application.port.out.StreetNetworkRepository;
import com.streetdom.application.port.out.result.ProviderMapMatchingResult;
import com.streetdom.application.port.out.result.ProviderMatchedEdge;
import com.streetdom.domain.model.geography.Location;
import com.streetdom.domain.model.geography.StreetSegment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapMatchingService {

    private final MapMatchingProvider mapMatchingProvider;
    private final StreetNetworkRepository streetNetworkRepository;

    public List<StreetSegment> match(List<Location> locations) {

        ProviderMapMatchingResult matchingResult = mapMatchingProvider.match(locations);

        List<List<Location>> matchedGeometries =
                matchingResult.matchedEdges()
                        .stream()
                        .map(ProviderMatchedEdge::edgeGeometry)
                        .toList();

        return streetNetworkRepository.findStreetSegmentsWithinDistance(matchedGeometries);
    }
}