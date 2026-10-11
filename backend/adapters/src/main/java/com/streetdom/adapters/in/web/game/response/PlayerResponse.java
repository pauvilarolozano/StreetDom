package com.streetdom.adapters.in.web.game.response;

import com.streetdom.domain.model.game.PlayerRole;

import java.time.Instant;
import java.util.UUID;

public record PlayerResponse(
        UUID userId,
        UUID gameId,
        PlayerRole role,
        Instant joinedAt
) {}
