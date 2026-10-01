package com.streetdom.adapters.out.persistence.entity;

import com.streetdom.domain.model.game.StreetSegmentState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "game_map_street_segment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GameMapStreetSegmentEntity {

    @EmbeddedId
    private GameMapStreetSegmentId id;

    /* POR SI MAS ADELANTE QUEREMOS OBTENER EL SEGMENT AL OBTENER LA ENTITY
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("streetSegmentId")
    @JoinColumn(name = "street_segment_id", nullable = false)
    private StreetSegmentEntity streetSegment;
    */

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private StreetSegmentState state;

}
