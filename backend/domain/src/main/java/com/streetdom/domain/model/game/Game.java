package com.streetdom.domain.model.game;

import com.streetdom.domain.exception.game.GameCannotBeFinishedException;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Game {
    private final UUID id;
    private final Instant createdAt;
    private GameState state;
    private Instant startedAt;
    private Instant finishedAt;

    @Builder
    private Game(
            UUID id,
            GameState state,
            Instant createdAt,
            Instant startedAt,
            Instant finishedAt
    ) {
        this.id = Objects.requireNonNull(id);
        this.state = Objects.requireNonNull(state);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;

        validateState();

    }

    public static Game create(Instant now) {
        return new Game(
                UUID.randomUUID(),
                GameState.CREATED,
                now,
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

        if (state != GameState.CREATED && state != GameState.IN_PROGRESS) {
            throw new GameCannotBeFinishedException(id);
        }

        state = GameState.FINISHED;
        finishedAt = now;
    }

    public boolean canProcessMovements() {
        return state == GameState.IN_PROGRESS;
    }

    private void validateState() {

        if (state == GameState.CREATED && (startedAt != null || finishedAt != null)) {
            throw new IllegalArgumentException("Created game cannot have start or finish dates");
        }

        if (state == GameState.IN_PROGRESS && (startedAt == null || finishedAt != null)) {
            throw new IllegalArgumentException("Game in progress must have a start date and no finish date");
        }

        if (state == GameState.FINISHED && finishedAt == null) {
            throw new IllegalArgumentException("Finished game must have a finish date");
        }
    }

}
