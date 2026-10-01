package com.streetdom.adapters.out.persistence.entity;

import com.streetdom.domain.model.game.PlayerRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Enumerated(EnumType.STRING)
    private PlayerRole role;
}
