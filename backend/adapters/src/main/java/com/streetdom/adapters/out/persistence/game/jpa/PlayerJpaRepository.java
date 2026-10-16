package com.streetdom.adapters.out.persistence.game.jpa;

import com.streetdom.adapters.out.persistence.game.entities.PlayerEntity;
import com.streetdom.adapters.out.persistence.game.entities.PlayerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlayerJpaRepository extends JpaRepository<PlayerEntity, PlayerId> {
    List<PlayerEntity> findAllByPlayerIdGameId(UUID gameId);
}
