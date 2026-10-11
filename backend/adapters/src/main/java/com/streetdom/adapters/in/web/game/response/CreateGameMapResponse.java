package com.streetdom.adapters.in.web.game.response;

import java.util.UUID;

public record CreateGameMapResponse(
        UUID id,
        String name
) {}
