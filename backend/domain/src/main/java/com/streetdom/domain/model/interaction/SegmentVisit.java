package com.streetdom.domain.model.interaction;

import java.time.Instant;
import java.util.UUID;

public record SegmentVisit(
        //TODO: anadir eb el futuro -> UUID runId,
        UUID streetSegmentId,
        Instant detectedAt
) {}
