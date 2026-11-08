package com.streetdom.adapters.in.web.game.response;

import com.streetdom.domain.model.game.StreetSegmentState;

import java.util.List;
import java.util.UUID;

public record GameSegmentResponse(
        UUID streetSegmentId,
        List<Position> geometry,
        StreetSegmentState state
) {
    public record Position(double latitude, double longitude) {}
}
