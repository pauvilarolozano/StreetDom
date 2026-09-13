package com.streetdom.adapters.out.valhalla.model;

import java.util.List;

public record ValhallaEdgeData(
        long id,
        List<Coordinate> coordinates
) {}