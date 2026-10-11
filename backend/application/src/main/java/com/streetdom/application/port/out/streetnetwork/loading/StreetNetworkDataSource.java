package com.streetdom.application.port.out.streetnetwork.loading;

import com.streetdom.domain.model.geography.GeographicBounds;

import java.util.List;

public interface StreetNetworkDataSource {

    List<RoutingEdgeImportData> readEdges(GeographicBounds bounds);
}

