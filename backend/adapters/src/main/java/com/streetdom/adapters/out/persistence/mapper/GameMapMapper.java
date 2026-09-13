package com.streetdom.adapters.out.persistence.mapper;

import com.streetdom.adapters.out.persistence.entity.GameMapEntity;
import com.streetdom.domain.model.GameMap;
import org.springframework.stereotype.Component;

@Component
public class GameMapMapper {

    public GameMap toDomain(GameMapEntity entity) {
        return GameMap.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public GameMapEntity toEntity(GameMap gameMap) {
        return GameMapEntity.builder()
                .id(gameMap.getId())
                .name(gameMap.getName())
                .build();
    }
}
