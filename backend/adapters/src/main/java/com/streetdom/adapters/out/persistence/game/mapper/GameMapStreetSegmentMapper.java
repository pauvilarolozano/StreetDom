package com.streetdom.adapters.out.persistence.game.mapper;

import com.streetdom.adapters.out.persistence.game.entities.GameMapStreetSegmentEntity;
import com.streetdom.adapters.out.persistence.game.entities.GameMapStreetSegmentId;
import com.streetdom.domain.model.game.GameMapStreetSegment;
import org.springframework.stereotype.Component;

@Component
public class GameMapStreetSegmentMapper {

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
