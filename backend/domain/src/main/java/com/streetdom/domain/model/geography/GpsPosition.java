package com.streetdom.domain.model.geography;

import java.time.Instant;
import java.util.Objects;

public record GpsPosition(
        Location location,
        Instant recordedAt,
        Double accuracyMeters
) {
    public GpsPosition {
        Objects.requireNonNull(location);
        Objects.requireNonNull(recordedAt);

        if (accuracyMeters != null && accuracyMeters < 0) {
            throw new IllegalArgumentException("Accuracy must be greater than or equal to 0");
        }
    }
}