package com.streetdom.domain.model.game;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class GameMapStreetSegment {

    private final UUID gameMapId;
    private final UUID streetSegmentId;
    private StreetSegmentState state;

    @Builder
    private GameMapStreetSegment(
            UUID gameMapId,
            UUID streetSegmentId,
            StreetSegmentState state
    ) {
        this.gameMapId = Objects.requireNonNull(gameMapId);
        this.streetSegmentId = Objects.requireNonNull(streetSegmentId);
        this.state = Objects.requireNonNull(state);
    }

    public static GameMapStreetSegment create(
            UUID gameMapId,
            UUID streetSegmentId,
            StreetSegmentState initialState
    ) {
        return new GameMapStreetSegment(
                gameMapId,
                streetSegmentId,
                initialState
        );
    }

    public void infect() {
        this.state = StreetSegmentState.INFECTED;
    }

    public void disinfect() {
        this.state = StreetSegmentState.CLEAN;
    }
}