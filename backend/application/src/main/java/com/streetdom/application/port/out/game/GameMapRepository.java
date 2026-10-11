package com.streetdom.application.port.out.game;

import com.streetdom.domain.model.game.GameMap;
import java.util.Optional;
import java.util.UUID;

public interface GameMapRepository {

    Optional<GameMap> findById(UUID id);

    void save(GameMap gameMap);
    boolean existsById(UUID id);

}
