package com.streetdom.domain.model.game;

import lombok.Builder;
import lombok.Getter;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Player {
    private final UUID userId;
    private final UUID gameId;
    private final Instant joinedAt;
    private PlayerRole role;

    @Builder
    private Player (UUID userId, UUID gameId, Instant joinedAt, PlayerRole role) {
        this.userId = Objects.requireNonNull(userId);
        this.gameId = Objects.requireNonNull(gameId);
        this.joinedAt = Objects.requireNonNull(joinedAt);
        this.role = Objects.requireNonNull(role);
    }

    public static Player create(UUID userId, UUID gameId, Instant now) {
        return new Player(
                userId,
                gameId,
                now,
                PlayerRole.UNASSIGNED
        );
    }

    public void assignInfectedRole() {
        this.role = PlayerRole.INFECTED;
    }

    public void assignSurvivorRole() {
        this.role = PlayerRole.SURVIVOR;
    }

    public void assignMedicRole() {
        this.role = PlayerRole.MEDIC;
    }
}


