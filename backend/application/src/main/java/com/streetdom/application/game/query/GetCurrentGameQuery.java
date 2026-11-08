package com.streetdom.application.game.query;

import java.util.UUID;

public record GetCurrentGameQuery(
        UUID userId
) {}
