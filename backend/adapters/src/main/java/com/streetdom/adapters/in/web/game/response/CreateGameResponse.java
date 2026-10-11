package com.streetdom.adapters.in.web.game.response;

import com.streetdom.domain.model.game.GameState;

import java.util.UUID;

public record CreateGameResponse(
        UUID gameId,
        UUID gameMapId,
        GameState state
) {}
