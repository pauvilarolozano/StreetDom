package com.streetdom.adapters.in.web.response;

import lombok.Builder;
import java.util.List;
import java.util.UUID;

@Builder
public record StreetSegmentResponse(
        UUID id,
        List<LocationResponse> geometry
) {}
