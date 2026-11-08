package com.streetdom.adapters.out.persistence.game.entities;

import com.streetdom.domain.model.game.StreetSegmentState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "game_street_segment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GameStreetSegmentEntity {

    @EmbeddedId
    private GameStreetSegmentId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private StreetSegmentState state;

}
