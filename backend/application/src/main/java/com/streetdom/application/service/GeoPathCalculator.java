package com.streetdom.application.service;

import com.streetdom.domain.model.Location;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GeoPathCalculator {

    private static final double EARTH_RADIUS_METERS = 6_371_000;

    public double distance(Location x, Location y) {
        double latX = Math.toRadians(x.latitude());
        double latY = Math.toRadians(y.latitude());

        double deltaLat = Math.toRadians(y.latitude() - x.latitude());
        double deltaLon = Math.toRadians(y.longitude() - x.longitude());

        double latitudeContribution   = Math.sin(deltaLat/2) * Math.sin(deltaLat/2);
        double longitudeContribution  = Math.cos(latX) * Math.cos(latY) * Math.sin(deltaLon/2) * Math.sin(deltaLon/2);

        double haversineValue = latitudeContribution + longitudeContribution ;
        double centralAngle = 2 * Math.atan2(Math.sqrt(haversineValue), Math.sqrt(1 - haversineValue));

        return EARTH_RADIUS_METERS * centralAngle;
    }

    public double length(List<Location> locations) {
        double totalLength = 0;

        for (int i = 1; i < locations.size(); i++) {
            totalLength += distance(locations.get(i - 1), locations.get(i));
        }

        return totalLength;
    }

    public Location interpolate(Location start, Location end, double ratio) {
        double latitude = start.latitude() + (end.latitude() - start.latitude()) * ratio;
        double longitude = start.longitude() + (end.longitude() - start.longitude()) * ratio;

        return new Location(latitude, longitude);
    }
}
