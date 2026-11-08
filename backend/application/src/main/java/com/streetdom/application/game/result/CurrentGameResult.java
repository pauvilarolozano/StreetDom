package com.streetdom.application.game.result;

import com.streetdom.domain.model.game.GameState;

import java.time.Instant;
import java.util.UUID;
import java.util.List;

public record CurrentGameResult(
        UUID gameId,
        GameState state,
        Instant createdAt,
        Instant startedAt,
        Instant finishedAt,
        PlayerResult player,
        List<GameSegmentResult> segments
) {}
