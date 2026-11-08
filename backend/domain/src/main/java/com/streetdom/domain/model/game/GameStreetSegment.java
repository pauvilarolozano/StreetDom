package com.streetdom.domain.model.game;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class GameStreetSegment {

    private final UUID gameId;
    private final UUID streetSegmentId;
    private StreetSegmentState state;

    @Builder
    private GameStreetSegment(
            UUID gameId,
            UUID streetSegmentId,
            StreetSegmentState state
    ) {
        this.gameId = Objects.requireNonNull(gameId);
        this.streetSegmentId = Objects.requireNonNull(streetSegmentId);
        this.state = Objects.requireNonNull(state);
    }

    public static GameStreetSegment create(
            UUID gameId,
            UUID streetSegmentId
    ) {
        return new GameStreetSegment(
                gameId,
                streetSegmentId,
                StreetSegmentState.CLEAN
        );
    }

    public void infect() {
        this.state = StreetSegmentState.INFECTED;
    }

    public void disinfect() {
        this.state = StreetSegmentState.CLEAN;
    }
}
