package com.streetdom.adapters.out.persistence.entity;

import com.streetdom.domain.model.game.GameState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "game")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GameEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID gameMapId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GameState state;

    private Instant startedAt;

    private Instant finishedAt;

}
