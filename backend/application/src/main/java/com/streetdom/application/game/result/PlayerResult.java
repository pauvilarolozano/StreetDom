package com.streetdom.application.game.result;

import com.streetdom.domain.model.game.PlayerRole;

import java.time.Instant;
import java.util.UUID;

public record PlayerResult(
        UUID userId,
        UUID gameId,
        PlayerRole role,
        Instant joinedAt
) {}
