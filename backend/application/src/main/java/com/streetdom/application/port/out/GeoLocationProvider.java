package com.streetdom.application.port.out;

import com.streetdom.domain.model.Location;

public interface GeoLocationProvider {

    String getCell(Location location);

}
