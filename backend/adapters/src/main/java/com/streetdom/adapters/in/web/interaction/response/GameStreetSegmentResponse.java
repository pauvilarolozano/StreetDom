package com.streetdom.adapters.in.web.interaction.response;

import com.streetdom.domain.model.game.StreetSegmentState;

import java.util.UUID;

public record GameStreetSegmentResponse(
        UUID gameId,
        UUID streetSegmentId,
        StreetSegmentState state
) {}
