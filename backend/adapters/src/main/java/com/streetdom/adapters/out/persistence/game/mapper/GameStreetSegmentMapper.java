package com.streetdom.adapters.out.persistence.game.mapper;

import com.streetdom.adapters.out.persistence.game.entities.GameStreetSegmentEntity;
import com.streetdom.adapters.out.persistence.game.entities.GameStreetSegmentId;
import com.streetdom.domain.model.game.GameStreetSegment;
import org.springframework.stereotype.Component;

@Component
public class GameStreetSegmentMapper {

    public GameStreetSegment toDomain(GameStreetSegmentEntity entity) {
        return GameStreetSegment.builder()
                .gameId(entity.getId().getGameId())
                .streetSegmentId(entity.getId().getStreetSegmentId())
                .state(entity.getState())
                .build();
    }

    public GameStreetSegmentEntity toEntity(GameStreetSegment gameStreetSegment) {
        return GameStreetSegmentEntity.builder()
                .id(new GameStreetSegmentId(
                        gameStreetSegment.getGameId(),
                        gameStreetSegment.getStreetSegmentId()
                ))
                .state(gameStreetSegment.getState())
                .build();
    }
}
