package com.streetdom.application.streetnetwork.matching;

import com.streetdom.application.port.out.streetnetwork.matching.MapMatchingProvider;
import com.streetdom.application.port.out.streetnetwork.StreetNetworkRepository;
import com.streetdom.application.port.out.streetnetwork.matching.ProviderMapMatchingResult;
import com.streetdom.application.port.out.streetnetwork.matching.ProviderMatchedEdge;
import com.streetdom.domain.model.geography.Location;
import com.streetdom.domain.model.streetnetwork.StreetSegment;
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