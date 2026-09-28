package com.streetdom.domain.model.geography;

import java.time.Instant;

public record GpsPosition(
        double latitude,
        double longitude,
        Instant recordedAt,
        Double accuracyMeters
) {}