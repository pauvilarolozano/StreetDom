package com.streetdom.adapters.out.persistence.game.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GameStreetSegmentId implements Serializable {

    @Column(name = "game_id")
    private UUID gameId;

    @Column(name = "street_segment_id")
    private UUID streetSegmentId;
}
