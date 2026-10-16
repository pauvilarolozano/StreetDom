package com.streetdom.adapters.out.persistence.game.mapper;

import com.streetdom.adapters.out.persistence.game.entities.GameMapEntity;
import com.streetdom.domain.model.game.GameMap;
import org.springframework.stereotype.Component;

@Component
public class GameMapMapper {

    public GameMap toDomain(GameMapEntity entity) {
        return GameMap.builder()
                .id(entity.getId())
                .build();
    }

    public GameMapEntity toEntity(GameMap gameMap) {
        return GameMapEntity.builder()
                .id(gameMap.getId())
                .build();
    }
}
