package com.streetdom.domain.model.game;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class Player {
    private final UUID userId;
    private final UUID gameId;
    private PlayerRole role;

    @Builder
    private Player (UUID userId, UUID gameId, PlayerRole role) {
        this.userId = Objects.requireNonNull(userId);
        this.gameId = Objects.requireNonNull(gameId);
        this.role = Objects.requireNonNull(role);
    }

    public static Player create(UUID userId, UUID gameId, PlayerRole role) {

        return new Player(userId,gameId,role);

    }
}


