package com.streetdom.adapters.in.web.game.response;

import com.streetdom.domain.model.game.GameState;

import java.time.Instant;
import java.util.UUID;

public record FinishGameResponse(
        UUID gameId,
        GameState state,
        Instant finishedAt
) {}
