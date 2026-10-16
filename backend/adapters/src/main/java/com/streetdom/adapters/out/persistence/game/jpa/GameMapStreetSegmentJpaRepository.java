package com.streetdom.adapters.out.persistence.game.jpa;

import com.streetdom.adapters.out.persistence.game.entities.GameMapStreetSegmentEntity;
import com.streetdom.adapters.out.persistence.game.entities.GameMapStreetSegmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameMapStreetSegmentJpaRepository extends JpaRepository<GameMapStreetSegmentEntity, GameMapStreetSegmentId> {
}
