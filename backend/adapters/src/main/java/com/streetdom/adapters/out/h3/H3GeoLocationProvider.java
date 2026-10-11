package com.streetdom.adapters.out.h3;

import com.streetdom.application.port.out.geolocation.GeoLocationProvider;
import com.streetdom.domain.model.geography.GeographicCell;
import com.streetdom.domain.model.geography.Location;
import com.uber.h3core.H3Core;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class H3GeoLocationProvider implements GeoLocationProvider {

    private static final int H3_RESOLUTION = 8;   // rang allowed = [0-15]
    private final H3Core h3;

    @Override
    public GeographicCell getCell(Location location) {

        String cellValue = h3.latLngToCellAddress(
                location.latitude(),
                location.longitude(),
                H3_RESOLUTION
        );

        return new GeographicCell(cellValue);
    }

    @Override
    public Set<GeographicCell> getCellsWithinDistance(GeographicCell cell, int distance) {
        return h3.gridDisk(cell.value(), distance)
                .stream()
                .map(GeographicCell::new)
                .collect(Collectors.toSet());
    }
}
