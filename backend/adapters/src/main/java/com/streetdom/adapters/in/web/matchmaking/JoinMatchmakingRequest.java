package com.streetdom.adapters.in.web.matchmaking;

import com.streetdom.adapters.in.web.common.GpsPositionRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record JoinMatchmakingRequest(
        @NotNull @Valid GpsPositionRequest gpsPosition
) {}
