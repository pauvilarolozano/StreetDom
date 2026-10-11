package com.streetdom.application.port.out.streetnetwork.matching;


import com.streetdom.domain.model.geography.Location;
import java.util.List;

public interface MapMatchingProvider {

    ProviderMapMatchingResult match(List<Location> locationList);
}
