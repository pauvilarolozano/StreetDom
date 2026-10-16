package com.streetdom.domain.model.matchmaking;

import com.streetdom.domain.model.geography.GeographicCell;
import com.streetdom.domain.model.geography.GpsPosition;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record MatchmakingEntry(
        UUID userId,
        GpsPosition gpsPosition,
        GeographicCell cell,
        Instant joinedAt
) {

    public MatchmakingEntry {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(gpsPosition);
        Objects.requireNonNull(cell);
        Objects.requireNonNull(joinedAt);
    }
}