package com.streetdom.application.port.out;

import com.streetdom.application.port.out.result.RoutingEdgeImportData;
import com.streetdom.domain.model.geography.GeographicBounds;

import java.util.List;

public interface MapDataSource {

    List<RoutingEdgeImportData> readEdges(GeographicBounds bounds);
}
