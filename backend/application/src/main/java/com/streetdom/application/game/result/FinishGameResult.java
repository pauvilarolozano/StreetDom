package com.streetdom.application.game.result;

import com.streetdom.domain.model.game.GameState;

import java.time.Instant;
import java.util.UUID;

public record FinishGameResult(
        UUID gameId,
        GameState state,
        Instant finishedAt
) {}
