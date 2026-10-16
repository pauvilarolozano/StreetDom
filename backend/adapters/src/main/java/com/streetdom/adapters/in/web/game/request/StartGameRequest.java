package com.streetdom.adapters.in.web.game.request;

import com.streetdom.adapters.in.web.common.GpsPositionRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record StartGameRequest(
        @NotNull @Valid GpsPositionRequest gpsPosition
) {}
