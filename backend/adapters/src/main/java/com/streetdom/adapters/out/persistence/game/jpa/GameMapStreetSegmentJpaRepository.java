package com.streetdom.adapters.out.persistence.game.jpa;

import com.streetdom.adapters.out.persistence.game.entities.GameMapStreetSegmentEntity;
import com.streetdom.adapters.out.persistence.game.entities.GameMapStreetSegmentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface GameMapStreetSegmentJpaRepository extends JpaRepository<GameMapStreetSegmentEntity, GameMapStreetSegmentId> {
    List<GameMapStreetSegmentEntity> findAllByIdGameMapIdAndIdStreetSegmentIdIn(UUID gameMapId, Set<UUID> streetSegmentIds);
}
