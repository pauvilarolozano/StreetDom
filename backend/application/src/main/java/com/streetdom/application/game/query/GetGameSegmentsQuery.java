package com.streetdom.application.game.query;

import java.util.UUID;

public record GetGameSegmentsQuery(
        UUID userId,
        UUID gameId
    ) {}
