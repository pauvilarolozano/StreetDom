package com.streetdom.adapters.out.persistence.game.entities;

import com.streetdom.domain.model.game.PlayerRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "player")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlayerEntity {

    @EmbeddedId
    private PlayerId playerId;

    @Column(nullable = false)
    private Instant joinedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlayerRole role;
}
