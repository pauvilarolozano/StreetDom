package com.streetdom.adapters.in.web.interaction.response;

import java.util.List;

public record MovementUpdateResponse(
        List<GameStreetSegmentResponse> changedSegments
) {}
