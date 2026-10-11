package com.streetdom.application.port.out.geolocation;

import com.streetdom.domain.model.geography.GeographicCell;
import com.streetdom.domain.model.geography.Location;

import java.util.Set;

public interface GeoLocationProvider {

    GeographicCell getCell(Location location);
    Set<GeographicCell> getCellsWithinDistance(GeographicCell cell, int distance);

}
