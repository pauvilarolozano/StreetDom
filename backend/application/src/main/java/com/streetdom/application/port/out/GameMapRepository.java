package com.streetdom.application.port.out;

import com.streetdom.domain.model.GameMap;

import java.util.Optional;
import java.util.UUID;

public interface GameMapRepository {

    void save(GameMap gameMap);

    Optional<GameMap> findById(UUID id);

}
