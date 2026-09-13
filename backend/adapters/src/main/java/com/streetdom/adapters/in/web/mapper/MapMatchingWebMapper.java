package com.streetdom.adapters.in.web.mapper;

import com.streetdom.adapters.in.web.request.MapMatchingRequest;
import com.streetdom.adapters.in.web.response.LocationResponse;
import com.streetdom.adapters.in.web.response.MapMatchingResponse;
import com.streetdom.adapters.in.web.response.StreetSegmentResponse;
import com.streetdom.application.command.MapMatchingCommand;
import com.streetdom.application.port.in.result.MapMatchingResult;
import com.streetdom.domain.model.Location;
import com.streetdom.domain.model.StreetSegment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapMatchingWebMapper {

    public MapMatchingCommand toCommand(MapMatchingRequest request) {
        List<Location> locations =
                request.locations()
                        .stream()
                        .map(location -> new Location(location.latitude(), location.longitude()))
                        .toList();

        return new MapMatchingCommand(locations);
    }

    public MapMatchingResponse toResponse(MapMatchingResult result) {

        return MapMatchingResponse.builder()
                .streetSegments(result.streetSegments().stream()
                        .map(this::toStreetSegmentResponse)
                        .toList()
                )
                .build();
    }

    private StreetSegmentResponse toStreetSegmentResponse
            (StreetSegment streetSegment) {

        return StreetSegmentResponse.builder()
                .id(streetSegment.getId())
                .geometry(streetSegment.getGeometry().stream()
                        .map(this::toLocationResponse)
                        .toList()
                )
                .build();

    }

    private LocationResponse toLocationResponse(Location location) {
        return new LocationResponse(location.latitude(), location.longitude());
    }
}
