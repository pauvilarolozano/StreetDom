package com.streetdom.application.interaction;

import com.streetdom.application.streetnetwork.matching.MapMatchingService;
import com.streetdom.domain.model.geography.Location;
import com.streetdom.domain.model.interaction.SegmentVisit;
import com.streetdom.domain.model.streetnetwork.StreetSegment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessMovementService  implements ProcessMovementUseCase {

    private final MapMatchingService mapMatchingService;
    private  final InteractionService interactionService;
    private final Clock clock;

    @Override
    public List<StreetSegment> process(ProcessMovementCommand command) {

        List<Location> locations =
                command.locations().stream()
                        .map(gpsPosition -> new Location(
                                gpsPosition.location().latitude(),
                                gpsPosition.location().longitude())
                        )
                        .toList();

        List<StreetSegment> matchedSegments = mapMatchingService.match(locations);

        Instant detectedAt = clock.instant();

        List<SegmentVisit> visitedSegments =
                matchedSegments.stream()
                        .map(segment -> new SegmentVisit(segment.getId(), detectedAt))
                        .toList();

        return List.of();
    }
}
