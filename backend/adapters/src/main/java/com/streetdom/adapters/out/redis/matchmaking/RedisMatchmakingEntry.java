package com.streetdom.adapters.out.redis.matchmaking;

import java.time.Instant;
import java.util.UUID;

public record RedisMatchmakingEntry(
        UUID userId,
        double latitude,
        double longitude,
        Instant recordedAt,
        Double accuracyMeters,
        String cellValue,
        Instant joinedAt
) {
}
