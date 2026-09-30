package com.streetdom.domain.model.game;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class GameMap {
    private final UUID id;
    private final String name;

    @Builder
    private GameMap (UUID id, String name) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);

        if (name.isBlank()) {
            throw new IllegalArgumentException("Game map name cannot be blank");
        }
    }

    public static GameMap create(String name) {
        return new GameMap(
                UUID.randomUUID(),
                name
        );
    }

}
