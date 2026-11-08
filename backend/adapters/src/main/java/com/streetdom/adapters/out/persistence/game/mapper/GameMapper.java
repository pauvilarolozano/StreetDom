package com.streetdom.adapters.out.persistence.game.mapper;

import com.streetdom.adapters.out.persistence.game.entities.GameEntity;
import com.streetdom.domain.model.game.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public Game toDomain(GameEntity entity) {
        return Game.builder()
                .id(entity.getId())
                .state(entity.getState())
                .createdAt(entity.getCreatedAt())
                .startedAt(entity.getStartedAt())
                .finishedAt(entity.getFinishedAt())
                .build();
    }

    public GameEntity toEntity(Game game) {
        return GameEntity.builder()
                .id(game.getId())
                .state(game.getState())
                .createdAt(game.getCreatedAt())
                .startedAt(game.getStartedAt())
                .finishedAt(game.getFinishedAt())
                .build();
    }

}
