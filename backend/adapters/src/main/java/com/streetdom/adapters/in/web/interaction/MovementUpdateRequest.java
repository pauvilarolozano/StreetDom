package com.streetdom.adapters.in.web.interaction;

import com.streetdom.adapters.in.web.common.GpsPositionRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MovementUpdateRequest(
        @NotEmpty
        @Size(max = 50)
        List<@NotNull @Valid GpsPositionRequest> positions
) {}
