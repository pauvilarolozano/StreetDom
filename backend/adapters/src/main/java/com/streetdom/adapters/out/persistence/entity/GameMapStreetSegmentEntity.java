package com.streetdom.adapters.out.persistence.entity;

import com.streetdom.domain.model.StreetSegmentState;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("gameMapId")
    @JoinColumn(name = "game_map_id", nullable = false)
    private GameMapEntity gameMap;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("streetSegmentId")
    @JoinColumn(name = "street_segment_id", nullable = false)
    private StreetSegmentEntity streetSegment;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private StreetSegmentState state;

}
