package com.streetdom.application.game.result;

import com.streetdom.domain.model.game.GameState;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record StartGameResult(
        UUID gameId,
        GameState state,
        Instant startedAt,
        List<PlayerResult> players
) {}
