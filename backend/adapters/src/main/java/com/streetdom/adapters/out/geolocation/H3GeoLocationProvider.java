package com.streetdom.adapters.out.geolocation;

import com.streetdom.application.port.out.GeoLocationProvider;
import com.streetdom.domain.model.Location;
import com.uber.h3core.H3Core;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class H3GeoLocationProvider implements GeoLocationProvider {

    private static final int H3_RESOLUTION = 9;   // rang allowed = [0-15]
    private final H3Core h3;

    @Override
    public String getCell(Location location) {
        return h3.latLngToCellAddress(
                location.latitude(),
                location.longitude(),
                H3_RESOLUTION
        );
    }
}
