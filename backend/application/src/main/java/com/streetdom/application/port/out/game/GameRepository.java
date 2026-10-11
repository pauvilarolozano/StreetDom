package com.streetdom.application.port.out.game;

import com.streetdom.domain.model.game.Game;

import java.util.Optional;
import java.util.UUID;


public interface GameRepository {

    Optional<Game> findById(UUID id);

    void save(Game game);
    boolean existsById(UUID id);
    boolean existsByGameMapId(UUID gameMapId);
}
