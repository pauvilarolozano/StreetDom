package com.streetdom.adapters.in.web.interaction;

import com.streetdom.domain.model.game.StreetSegmentState;

import java.util.UUID;

public record GameMapStreetSegmentResponse(
        UUID gameMapId,
        UUID streetSegmentId,
        StreetSegmentState state
) {}
