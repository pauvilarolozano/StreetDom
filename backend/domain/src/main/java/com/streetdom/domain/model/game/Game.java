package com.streetdom.domain.model.game;

import java.time.Instant;
import java.util.UUID;

public class Game {
    private UUID id;
    private UUID gameMapId;
    private GameState state;
    private Instant startedAt;
    private Instant finishedAt;
}
