package com.streetdom.application.streetnetwork.matching;

import com.streetdom.application.port.out.streetnetwork.matching.MapMatchingProvider;
import com.streetdom.application.port.out.streetnetwork.StreetNetworkRepository;
import com.streetdom.application.port.out.streetnetwork.matching.ProviderMapMatchingResult;
import com.streetdom.application.port.out.streetnetwork.matching.ProviderMatchedEdge;
import com.streetdom.domain.model.geography.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MapMatchingService {

    private final MapMatchingProvider mapMatchingProvider;
    private final StreetNetworkRepository streetNetworkRepository;

    public Set<UUID> match(List<Location> locations) {

        ProviderMapMatchingResult matchingResult = mapMatchingProvider.match(locations);

        if (matchingResult.matchedEdges().isEmpty()) {
            return Set.of();
        }

        List<List<Location>> matchedGeometries =
                matchingResult.matchedEdges()
                        .stream()
                        .map(ProviderMatchedEdge::edgeGeometry)
                        .toList();

        return streetNetworkRepository.findStreetSegmentIdsNearEdges(matchedGeometries);
    }
}
