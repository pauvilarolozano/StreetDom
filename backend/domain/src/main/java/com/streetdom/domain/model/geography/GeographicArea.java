package com.streetdom.domain.model.geography;

import java.util.List;

public record GeographicArea(
        List<List<Location>> polygons
) {}
