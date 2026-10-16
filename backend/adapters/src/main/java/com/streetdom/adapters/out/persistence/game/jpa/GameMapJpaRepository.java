package com.streetdom.adapters.out.persistence.game.jpa;

import com.streetdom.adapters.out.persistence.game.entities.GameMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface GameMapJpaRepository extends JpaRepository<GameMapEntity, UUID> {
}
