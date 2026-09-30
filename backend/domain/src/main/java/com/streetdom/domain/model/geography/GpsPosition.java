package com.streetdom.domain.model.geography;

import java.time.Instant;
import java.util.Objects;

public record GpsPosition(
        double latitude,
        double longitude,
        Instant recordedAt,
        Double accuracyMeters
) {
    public GpsPosition {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Invalid latitude");
        }

        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Invalid longitude");
        }

        Objects.requireNonNull(recordedAt);

        if (accuracyMeters != null && accuracyMeters < 0) {
            throw new IllegalArgumentException("Accuracy must be greater than or equal to 0");
        }
    }
}