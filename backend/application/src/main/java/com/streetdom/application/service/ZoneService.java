package com.streetdom.application.service;

import com.streetdom.application.port.in.ZoneUseCase;
import com.streetdom.application.port.out.GeoLocationProvider;
import com.streetdom.domain.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZoneService implements ZoneUseCase {

    private final GeoLocationProvider geoLocationProvider;


    @Override
    public String getCell(double latitude, double longitude) {
        Location location = new Location(latitude,longitude);
        return geoLocationProvider.getCell(location);
    }
}
