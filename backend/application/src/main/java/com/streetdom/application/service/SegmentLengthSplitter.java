package com.streetdom.application.service;

import com.streetdom.domain.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SegmentLengthSplitter {

    private static final double MAX_LENGTH_METERS = 29.5;

    private final GeoPathCalculator geoPathCalculator;

    public List<List<Location>> split(List<Location> locations) {

        List<List<Location>> segments = new ArrayList<>();
        if (locations.size() < 2) {
            return segments;
        }

        List<Location> currentSegment = new ArrayList<>();
        currentSegment.add(locations.getFirst());

        double currentLength = 0;
        for (int i = 1; i < locations.size(); i++) {

            Location start = locations.get(i - 1);
            Location end = locations.get(i);

            double remainingDistance = geoPathCalculator.distance(start, end);

            Location currentStart = start;
            while (currentLength + remainingDistance > MAX_LENGTH_METERS) {

                double distanceNeeded = MAX_LENGTH_METERS - currentLength;
                double ratio = distanceNeeded / remainingDistance;

                Location cutPoint = geoPathCalculator.interpolate(currentStart, end, ratio);

                currentSegment.add(cutPoint);
                segments.add(List.copyOf(currentSegment));

                currentSegment.clear();
                currentSegment.add(cutPoint);

                currentStart = cutPoint;
                remainingDistance = geoPathCalculator.distance(currentStart, end);
                currentLength = 0;
            }

            currentSegment.add(end);
            currentLength += remainingDistance;
        }

        if (currentSegment.size() > 1) {
            segments.add(List.copyOf(currentSegment));
        }

        return segments;
    }
}