package com.streetdom.domain.model.geography;

import java.util.Objects;

public record GeographicBounds(
        Location southWest,
        Location northEast
) {
    public GeographicBounds {
        Objects.requireNonNull(southWest);
        Objects.requireNonNull(northEast);
    }
}
