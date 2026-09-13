package com.streetdom.adapters.out.valhalla.mapper;

import com.streetdom.adapters.out.valhalla.ValhallaPolylineDecoder;
import com.streetdom.adapters.out.valhalla.dto.ValhallaMatchedEdgeResponse;
import com.streetdom.adapters.out.valhalla.dto.ValhallaTraceResponse;
import com.streetdom.application.port.out.result.ProviderMapMatchingResult;
import com.streetdom.application.port.out.result.ProviderMatchedEdge;
import com.streetdom.domain.model.Location;
import com.streetdom.domain.model.RoutingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ValhallaMapMatchingMapper {

    private final ValhallaPolylineDecoder polylineDecoder;

    public ProviderMapMatchingResult toResult(ValhallaTraceResponse response) {

        List<Location> fullShape = polylineDecoder.decode(response.shape());
        List<ProviderMatchedEdge> matchedEdges = new ArrayList<>();

        for (ValhallaMatchedEdgeResponse edge : response.edges()) {

            List<Location> geometry = new ArrayList<>(
                    fullShape.subList(
                            edge.begin_shape_index(),
                            edge.end_shape_index() + 1
                    )
            );

            matchedEdges.add(
                    new ProviderMatchedEdge(
                            String.valueOf(edge.id()),
                            geometry
                    )
            );
        }

        return new ProviderMapMatchingResult(
                RoutingProvider.VALHALLA,
                matchedEdges
        );
    }
}
