package com.streetdom.application.game.result;

import java.util.UUID;

public record CreateGameMapResult(
        UUID id,
        String name
) {}
