package com.streetdom.adapters.out.persistence.game.jpa;

import com.streetdom.adapters.out.persistence.game.entities.GameStreetSegmentEntity;
import com.streetdom.adapters.out.persistence.game.entities.GameStreetSegmentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface GameStreetSegmentJpaRepository extends JpaRepository<GameStreetSegmentEntity, GameStreetSegmentId> {
    List<GameStreetSegmentEntity> findAllByIdGameId(UUID gameId);

    List<GameStreetSegmentEntity> findAllByIdGameIdAndIdStreetSegmentIdIn(UUID gameId, Set<UUID> streetSegmentIds);
}
