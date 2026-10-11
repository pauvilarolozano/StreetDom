package com.streetdom.adapters.out.persistence.game;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameJpaRepository extends JpaRepository<GameEntity, UUID> {
    boolean existsByGameMapId(UUID gameMapId);
}
