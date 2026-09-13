package com.streetdom.application.port.out;


import com.streetdom.application.port.out.result.ProviderMapMatchingResult;
import com.streetdom.domain.model.Location;
import java.util.List;

public interface MapMatchingProvider {

    ProviderMapMatchingResult match(List<Location> locationList);
}
