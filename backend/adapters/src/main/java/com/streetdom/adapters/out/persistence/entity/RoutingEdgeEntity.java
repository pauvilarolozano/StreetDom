package com.streetdom.adapters.out.persistence.entity;

import com.streetdom.domain.model.RoutingProvider;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(
        name = "routing_edge",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"provider", "external_id"}
                )
        }
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RoutingEdgeEntity implements Persistable<UUID> {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private RoutingProvider provider;

    @Column(name = "external_id", nullable = false)
    private String externalId;

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