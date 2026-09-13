package com.streetdom.application.port.in;

import com.streetdom.application.command.MapMatchingCommand;
import com.streetdom.application.port.in.result.MapMatchingResult;

public interface MapMatchingUseCase {
    MapMatchingResult match(MapMatchingCommand command);
}
