package com.streetdom.adapters.in.web.game.response;

import com.streetdom.domain.model.game.GameState;

import java.time.Instant;
import java.util.UUID;
import java.util.List;

public record CurrentGameResponse(
        UUID gameId,
        GameState state,
        Instant createdAt,
        Instant startedAt,
        Instant finishedAt,
        PlayerResponse player,
        List<GameSegmentResponse> segments
) {}
