package com.streetdom.adapters.out.valhalla.mapper;

import com.streetdom.adapters.out.valhalla.model.ValhallaEdgeData;
import com.streetdom.application.port.out.result.RoutingEdgeImportData;
import com.streetdom.domain.model.Location;
import com.streetdom.domain.model.RoutingEdge;
import com.streetdom.domain.model.RoutingProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ValhallaRoutingEdgeImportMapper {

    public RoutingEdgeImportData toImportData(ValhallaEdgeData edge) {

        List<Location> geometry = edge.coordinates()
                .stream()
                .map(coordinate ->
                        new Location(
                                coordinate.latitude(),
                                coordinate.longitude()
                        )
                )
                .toList();

        return new RoutingEdgeImportData(
                RoutingProvider.VALHALLA,
                String.valueOf(edge.id()),
                geometry
        );
    }
}
