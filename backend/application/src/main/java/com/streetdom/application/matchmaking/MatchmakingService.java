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
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchmakingService implements MatchmakingUseCase {

    private static final int MIN_PLAYERS = 4;
    private static final int MAX_GRID_DISTANCE = 3;
    private static final Duration MAX_GPS_AGE = Duration.ofSeconds(60);

    private final GeoLocationProvider geoLocationProvider;
    private final MatchmakingRepository matchmakingRepository;
    private final Clock clock;

    @Override
    public JoinMatchmakingResult joinMatchmaking(JoinMatchmakingCommand command) {

        UUID userId = command.userId();
        GpsPosition gpsPosition = command.gpsPosition();
        Instant now = clock.instant();

        validateGpsPosition(gpsPosition, now);

        GeographicCell cell = geoLocationProvider.getCell(gpsPosition.location());
        MatchmakingEntry entry = new MatchmakingEntry(
                userId,
                gpsPosition,
                cell,
                now
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

    private void validateGpsPosition(GpsPosition gpsPosition, Instant now) {
        if (gpsPosition.recordedAt().isBefore(now.minus(MAX_GPS_AGE))) {
            throw new RuntimeException();
            //TODO: custom exception
        }
    }
}
