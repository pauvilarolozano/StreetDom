package com.streetdom.application.interaction;

import com.streetdom.domain.model.geography.GpsPosition;
import java.util.List;
import java.util.UUID;

public record ProcessMovementCommand(
        UUID userId,
        List<GpsPosition> locations
) {}