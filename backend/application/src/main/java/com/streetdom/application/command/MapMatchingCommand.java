package com.streetdom.application.command;

import com.streetdom.domain.model.Location;
import java.util.List;

public record MapMatchingCommand(
        List<Location> locations
) {}
