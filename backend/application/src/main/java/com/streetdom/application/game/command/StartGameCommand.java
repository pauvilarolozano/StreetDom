package com.streetdom.application.game.command;

import com.streetdom.domain.model.geography.GpsPosition;

import java.util.UUID;

public record StartGameCommand(
        UUID userId,
        GpsPosition gpsPosition
) {}
