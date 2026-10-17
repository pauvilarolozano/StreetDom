package com.streetdom.adapters.out.persistence.game.mapper;

import com.streetdom.adapters.out.persistence.game.entities.GameMapStreetSegmentEntity;
import com.streetdom.adapters.out.persistence.game.entities.GameMapStreetSegmentId;
import com.streetdom.domain.model.game.GameMapStreetSegment;
import org.springframework.stereotype.Component;

@Component
public class GameMapStreetSegmentMapper {

    public GameMapStreetSegment toDomain(GameMapStreetSegmentEntity entity) {
        return GameMapStreetSegment.builder()
                .gameMapId(entity.getId().getGameMapId())
                .streetSegmentId(entity.getId().getStreetSegmentId())
                .state(entity.getState())
                .build();
    }

    public GameMapStreetSegmentEntity toEntity(GameMapStreetSegment gameMapStreetSegment) {
        return GameMapStreetSegmentEntity.builder()
                .id(new GameMapStreetSegmentId(
                        gameMapStreetSegment.getGameMapId(),
                        gameMapStreetSegment.getStreetSegmentId()
                ))
                .state(gameMapStreetSegment.getState())
                .build();
    }
}
