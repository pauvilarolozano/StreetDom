package com.streetdom.application.port.out;

public interface GeoLocationProvider {

    String getCell(double latitude, double longitude);

}
