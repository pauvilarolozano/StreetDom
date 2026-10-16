package com.streetdom.adapters.in.web.game.response;

import com.streetdom.domain.model.game.GameState;

import java.time.Instant;
import java.util.UUID;

public record StartGameResponse(
        UUID gameId,
        UUID gameMapId,
        GameState state,
        Instant startedAt,
        PlayerResponse player
) {}
