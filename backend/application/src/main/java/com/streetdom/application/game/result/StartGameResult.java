package com.streetdom.application.game.result;

import com.streetdom.domain.model.game.GameState;

import java.time.Instant;
import java.util.UUID;

public record StartGameResult(
        UUID gameId,
        UUID gameMapId,
        GameState state,
        Instant startedAt,
        PlayerResult player
) {}
