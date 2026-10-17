package com.streetdom.adapters.out.persistence.game;

import com.streetdom.adapters.out.persistence.game.entities.GameMapStreetSegmentEntity;
import com.streetdom.adapters.out.persistence.game.entities.GameMapStreetSegmentId;
import com.streetdom.adapters.out.persistence.game.jpa.GameMapStreetSegmentJpaRepository;
import com.streetdom.adapters.out.persistence.game.mapper.GameMapStreetSegmentMapper;
import com.streetdom.application.port.out.game.GameMapStreetSegmentRepository;
import com.streetdom.domain.model.game.GameMapStreetSegment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GameMapStreetSegmentPersistenceAdapter implements GameMapStreetSegmentRepository {

    private final GameMapStreetSegmentJpaRepository gameMapStreetSegmentJpaRepository;
    private final GameMapStreetSegmentMapper mapper;

    @Override
    public List<GameMapStreetSegment> findByStreetSegmentIdsAndGameMapId(Set<UUID> streetSegmentIds, UUID gameMapId) {
        if (streetSegmentIds.isEmpty()) {
            return List.of();
        }

        return gameMapStreetSegmentJpaRepository.findAllByIdGameMapIdAndIdStreetSegmentIdIn(gameMapId, streetSegmentIds)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<GameMapStreetSegment> findByStreetSegmentIdAndGameMapId(UUID streetSegmentId, UUID gameMapId) {
        return gameMapStreetSegmentJpaRepository.findById(new GameMapStreetSegmentId(gameMapId, streetSegmentId))
                .map(mapper::toDomain);
    }

    @Override
    public void saveAll(List<GameMapStreetSegment> gameMapStreetSegments) {
        List<GameMapStreetSegmentEntity> entities = gameMapStreetSegments.stream()
                .map(mapper::toEntity)
                .toList();

        gameMapStreetSegmentJpaRepository.saveAll(entities);
    }
}
