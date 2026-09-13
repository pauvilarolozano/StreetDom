package com.streetdom.adapters.out.persistence.mapper;

import com.streetdom.domain.model.Location;
import org.locationtech.jts.geom.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class GeometryMapper {

    private static final int SRID = 4326;

    private final GeometryFactory geometryFactory =
            new GeometryFactory(
                    new PrecisionModel(),
                    4326
            );

    public LineString toLineString(List<Location> locations) {

        Coordinate[] coordinates =
                locations.stream()
                        .map(location -> new Coordinate(location.longitude(), location.latitude()))
                        .toArray(Coordinate[]::new);

        return geometryFactory.createLineString(coordinates);
    }

    public MultiLineString toMultiLineString(List<List<Location>> geometries) {

        LineString[] lineStrings =
                geometries.stream()
                        .map(this::toLineString)
                        .toArray(LineString[]::new);

        return geometryFactory.createMultiLineString(lineStrings);
    }

    public List<Location> toLocations(LineString lineString) {
        return Arrays.stream(lineString.getCoordinates())
                .map(coordinate ->
                        new Location(
                                coordinate.getY(),
                                coordinate.getX()
                        )
                )
                .toList();
    }
}
