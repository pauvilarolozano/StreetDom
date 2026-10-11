package com.streetdom.application.matchmaking.command;

import com.streetdom.domain.model.geography.GpsPosition;
import java.util.UUID;

public record JoinMatchmakingCommand(
        UUID userId,
        GpsPosition gpsPosition
) {}
