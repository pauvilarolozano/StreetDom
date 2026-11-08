package com.streetdom.adapters.in.web.game.response;

import java.util.List;
import java.util.UUID;

public record GameSegmentsResponse(
        UUID gameId,
        List<GameSegmentResponse> segments
) {}
