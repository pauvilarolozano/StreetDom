package com.streetdom.application.game.result;

import com.streetdom.domain.model.game.GameState;

import java.util.UUID;

public record CreateGameResult(
        UUID gameId,
        UUID gameMapId,
        GameState state
) {}
