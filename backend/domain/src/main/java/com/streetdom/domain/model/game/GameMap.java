package com.streetdom.domain.model.game;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class GameMap {
    private final UUID id;

    @Builder
    private GameMap (UUID id) {
        this.id = Objects.requireNonNull(id);
    }

    public static GameMap create() {
        return new GameMap(
                UUID.randomUUID()
        );
    }

}
