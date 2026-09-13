package com.streetdom.adapters.in.web.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MapMatchingRequest(
        @NotEmpty
        @Size(min = 2)
        List<LocationRequest> locations
) {}
