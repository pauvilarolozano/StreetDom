package com.streetdom.adapters.in.web.interaction;

import java.util.List;

public record MovementUpdateResponse(
        List<GameMapStreetSegmentResponse> changedSegments
) {}
