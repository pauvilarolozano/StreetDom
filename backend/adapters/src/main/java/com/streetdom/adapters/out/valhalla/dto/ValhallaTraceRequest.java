package com.streetdom.adapters.out.valhalla.dto;

import java.util.List;

public record ValhallaTraceRequest(
        List<ValhallaLocationRequest> shape,
        String costing,
        String shape_match
) {}
