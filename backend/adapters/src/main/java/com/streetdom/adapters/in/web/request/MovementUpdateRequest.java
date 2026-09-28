package com.streetdom.adapters.in.web.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MovementUpdateRequest(
        @NotEmpty
        @Size(max = 50)
        List<@Valid GpsPositionRequest> positions
) {}
