package com.streetdom.adapters.out.persistence.game;

import com.streetdom.domain.model.game.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public Game toDomain(GameEntity entity) {
        return Game.builder()
                .id(entity.getId())
                .gameMapId(entity.getGameMapId())
                .state(entity.getState())
                .startedAt(entity.getStartedAt())
                .finishedAt(entity.getFinishedAt())
                .build();
    }

    public GameEntity toEntity(Game game) {
        return GameEntity.builder()
                .id(game.getId())
                .gameMapId(game.getGameMapId())
                .state(game.getState())
                .startedAt(game.getStartedAt())
                .finishedAt(game.getFinishedAt())
                .build();
    }

}
