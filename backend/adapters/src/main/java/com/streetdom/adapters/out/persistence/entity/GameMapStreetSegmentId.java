package com.streetdom.adapters.out.persistence.entity;

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
public class GameMapStreetSegmentId implements Serializable {

    @Column(name = "game_map_id")
    private UUID gameMapId;

    @Column(name = "street_segment_id")
    private UUID streetSegmentId;
}
