package com.streetdom.adapters.out.persistence.game;

import com.streetdom.adapters.out.persistence.game.entities.GameStreetSegmentEntity;
import com.streetdom.adapters.out.persistence.game.entities.GameStreetSegmentId;
import com.streetdom.adapters.out.persistence.game.jpa.GameStreetSegmentJpaRepository;
import com.streetdom.adapters.out.persistence.game.mapper.GameStreetSegmentMapper;
import com.streetdom.application.port.out.game.GameStreetSegmentRepository;
import com.streetdom.domain.model.game.GameStreetSegment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GameStreetSegmentPersistenceAdapter implements GameStreetSegmentRepository {

    private final GameStreetSegmentJpaRepository gameStreetSegmentJpaRepository;
    private final GameStreetSegmentMapper mapper;

    @Override
    public List<GameStreetSegment> findByGameId(UUID gameId) {
        return gameStreetSegmentJpaRepository.findAllByIdGameId(gameId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<GameStreetSegment> findByStreetSegmentIdsAndGameId(Set<UUID> streetSegmentIds, UUID gameId) {
        if (streetSegmentIds.isEmpty()) {
            return List.of();
        }

        return gameStreetSegmentJpaRepository.findAllByIdGameIdAndIdStreetSegmentIdIn(gameId, streetSegmentIds)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<GameStreetSegment> findByStreetSegmentIdAndGameId(UUID streetSegmentId, UUID gameId) {
        return gameStreetSegmentJpaRepository.findById(new GameStreetSegmentId(gameId, streetSegmentId))
                .map(mapper::toDomain);
    }

    @Override
    public void saveAll(List<GameStreetSegment> gameStreetSegments) {
        List<GameStreetSegmentEntity> entities = gameStreetSegments.stream()
                .map(mapper::toEntity)
                .toList();

        gameStreetSegmentJpaRepository.saveAll(entities);
    }
}
