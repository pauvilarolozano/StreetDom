package com.streetdom.adapters.in.web.response;

import lombok.Builder;
import java.util.List;

@Builder
public record MapMatchingResponse(
        List<StreetSegmentResponse> streetSegments
) {}
