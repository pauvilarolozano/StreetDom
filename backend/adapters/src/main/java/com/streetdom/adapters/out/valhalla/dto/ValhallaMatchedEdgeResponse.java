package com.streetdom.adapters.out.valhalla.dto;

public record ValhallaMatchedEdgeResponse(
        long id,
        int begin_shape_index,
        int end_shape_index
) {}
