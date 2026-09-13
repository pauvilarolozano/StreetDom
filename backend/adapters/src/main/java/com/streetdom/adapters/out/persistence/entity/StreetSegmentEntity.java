package com.streetdom.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.LineString;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(name = "street_segment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StreetSegmentEntity implements Persistable<UUID> {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "routing_edge_id", nullable = false)
    private RoutingEdgeEntity routingEdge;

    @Column(columnDefinition = "geometry(LineString,4326)", nullable = false)
    private LineString geometry;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostPersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
}
