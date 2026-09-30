package com.streetdom.domain.model.game;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Game {
    private final UUID id;
    private final UUID gameMapId;
    private GameState state;
    private Instant startedAt;
    private Instant finishedAt;

    @Builder
    private Game(
            UUID id,
            UUID gameMapId,
            GameState state,
            Instant startedAt,
            Instant finishedAt
    ) {
        this.id = Objects.requireNonNull(id);
        this.gameMapId = Objects.requireNonNull(gameMapId);
        this.state = Objects.requireNonNull(state);
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;

        validateState();

    }

    public static Game create(UUID gameMapId) {
        return new Game(
                UUID.randomUUID(),
                gameMapId,
                GameState.CREATED,
                null,
                null
        );
    }

    public void start(Instant now) {
        Objects.requireNonNull(now);

        if (state != GameState.CREATED) {
            throw new IllegalStateException("Only created games can be started");
        }

        state = GameState.IN_PROGRESS;
        startedAt = now;
    }

    public void finish(Instant now) {

        Objects.requireNonNull(now);

        if (state != GameState.IN_PROGRESS) {
            throw new IllegalStateException("Only games in progress can be finished");
        }

        state = GameState.FINISHED;
        finishedAt = now;
    }

    private void validateState() {

        if (state == GameState.CREATED && (startedAt != null || finishedAt != null)) {
            throw new IllegalArgumentException("Created game cannot have start or finish dates");
        }

        if (state == GameState.IN_PROGRESS && (startedAt == null || finishedAt != null)) {
            throw new IllegalArgumentException("Game in progress must have a start date and no finish date");
        }

        if (state == GameState.FINISHED && (startedAt == null || finishedAt == null)) {
            throw new IllegalArgumentException("Finished game must have start and finish dates");
        }
    }

}
