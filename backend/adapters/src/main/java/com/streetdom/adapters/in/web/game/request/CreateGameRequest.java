package com.streetdom.adapters.in.web.game.request;

import com.streetdom.adapters.in.web.common.GpsPositionRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreateGameRequest(
        @NotNull @Valid GpsPositionRequest gpsPosition
) {}
