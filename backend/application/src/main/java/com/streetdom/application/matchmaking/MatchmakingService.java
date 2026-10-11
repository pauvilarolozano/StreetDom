package com.streetdom.application.matchmaking;

import com.streetdom.application.matchmaking.command.JoinMatchmakingCommand;
import com.streetdom.application.matchmaking.result.JoinMatchmakingResult;
import com.streetdom.application.matchmaking.result.MatchmakingStatus;
import com.streetdom.application.port.out.geolocation.GeoLocationProvider;
import com.streetdom.application.port.out.matchmaking.MatchmakingRepository;
import com.streetdom.domain.model.geography.GeographicCell;
import com.streetdom.domain.model.geography.GpsPosition;
import com.streetdom.domain.model.matchmaking.MatchmakingEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchmakingService implements MatchmakingUseCase {

    private final GeoLocationProvider geoLocationProvider;
    private final MatchmakingRepository matchmakingRepository;
    private Clock clock;

    @Override
    public JoinMatchmakingResult joinMatchmaking(JoinMatchmakingCommand command) {

        UUID userId = command.userId();
        GpsPosition gpsPosition = command.gpsPosition();

        GeographicCell cell = geoLocationProvider.getCell(gpsPosition.location());

        MatchmakingEntry entry = new MatchmakingEntry(
                userId,
                gpsPosition,
                cell,
                clock.instant()
        );

        matchmakingRepository.save(entry);

        Set<GeographicCell> nearbyCells = geoLocationProvider.getCellsWithinDistance(cell, 1);
        List<MatchmakingEntry> candidates = matchmakingRepository.findByCells(nearbyCells);

        // todavía no formamos Game

        return new JoinMatchmakingResult(
                MatchmakingStatus.SEARCHING,
                null
        );
    }
}
