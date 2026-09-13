package com.streetdom.application.port.in.result;

import com.streetdom.domain.model.StreetSegment;

import java.util.List;

public record MapMatchingResult(
        List<StreetSegment> streetSegments
) {}
