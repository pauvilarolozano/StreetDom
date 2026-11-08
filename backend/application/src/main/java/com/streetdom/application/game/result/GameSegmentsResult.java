package com.streetdom.application.game.result;

import java.util.List;
import java.util.UUID;

public record GameSegmentsResult(
        UUID gameId,
        List<GameSegmentResult> segments
) {}
