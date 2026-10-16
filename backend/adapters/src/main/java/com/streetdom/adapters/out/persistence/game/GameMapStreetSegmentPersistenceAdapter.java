package com.streetdom.adapters.out.persistence.game;

import com.streetdom.adapters.out.persistence.game.entities.GameMapStreetSegmentEntity;
import com.streetdom.adapters.out.persistence.game.jpa.GameMapStreetSegmentJpaRepository;
import com.streetdom.adapters.out.persistence.game.mapper.GameMapStreetSegmentMapper;
import com.streetdom.application.port.out.game.GameMapStreetSegmentRepository;
import com.streetdom.domain.model.game.GameMapStreetSegment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GameMapStreetSegmentPersistenceAdapter implements GameMapStreetSegmentRepository {

    private final GameMapStreetSegmentJpaRepository gameMapStreetSegmentJpaRepository;
    private final GameMapStreetSegmentMapper mapper;

    @Override
    public void saveAll(List<GameMapStreetSegment> gameMapStreetSegments) {
        List<GameMapStreetSegmentEntity> entities = gameMapStreetSegments.stream()
                .map(mapper::toEntity)
                .toList();

        gameMapStreetSegmentJpaRepository.saveAll(entities);
    }
}
