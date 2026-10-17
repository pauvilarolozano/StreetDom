package com.streetdom.adapters.out.persistence.game.jpa;

import com.streetdom.adapters.out.persistence.game.entities.PlayerEntity;
import com.streetdom.adapters.out.persistence.game.entities.PlayerId;
import com.streetdom.domain.model.game.GameState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PlayerJpaRepository extends JpaRepository<PlayerEntity, PlayerId> {

    @Query("""
        SELECT g.id
        FROM PlayerEntity p, GameEntity g
        WHERE p.playerId.gameId = g.id
          AND p.playerId.userId = :userId
          AND g.state = :state
        """)
    List<UUID> findGameIdsByUserIdAndGameState(@Param("userId") UUID userId, @Param("state") GameState state);

    List<PlayerEntity> findAllByPlayerIdGameId(UUID gameId);
}
