package com.streetdom.application.port.out.game;

import com.streetdom.domain.model.game.Game;
import com.streetdom.domain.model.game.GameState;

import java.util.Optional;
import java.util.UUID;


public interface GameRepository {

    Optional<Game> findById(UUID id);
    Optional<Game> findLatestByUserIdAndState(UUID userId, GameState state);

    void save(Game game);
    boolean existsById(UUID id);

}
