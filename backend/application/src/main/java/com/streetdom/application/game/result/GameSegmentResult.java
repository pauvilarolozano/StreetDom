package com.streetdom.application.game.result;

import com.streetdom.domain.model.game.StreetSegmentState;
import com.streetdom.domain.model.geography.Location;

import java.util.List;
import java.util.UUID;

public record GameSegmentResult(
        UUID streetSegmentId,
        List<Location> geometry,
        StreetSegmentState state
) {}
