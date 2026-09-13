package com.streetdom.adapters.out.valhalla.dto;

import java.util.List;

public record ValhallaTraceResponse(
        String shape,
        List<ValhallaMatchedEdgeResponse> edges
) {}
