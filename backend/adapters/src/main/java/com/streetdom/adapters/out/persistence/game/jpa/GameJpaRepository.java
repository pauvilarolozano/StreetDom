package com.streetdom.adapters.out.persistence.game.jpa;

import com.streetdom.adapters.out.persistence.game.entities.GameEntity;
import com.streetdom.domain.model.game.GameState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GameJpaRepository extends JpaRepository<GameEntity, UUID> {

    @Query("""
        SELECT g
        FROM GameEntity g, PlayerEntity p
        WHERE p.playerId.gameId = g.id
          AND p.playerId.userId = :userId
          AND g.state = :state
        ORDER BY g.createdAt DESC, g.id DESC
        """)
    List<GameEntity> findByUserIdAndState(
            @Param("userId") UUID userId,
            @Param("state") GameState state,
            Pageable pageable
    );

}
