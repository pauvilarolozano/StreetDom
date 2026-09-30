package com.streetdom.domain.model.geography;

import lombok.Builder;

import java.util.Objects;
import java.util.UUID;

public record RoutingEdge(
        UUID id,
        RoutingProvider provider,
        String externalId) {

    @Builder
    public RoutingEdge(
            UUID id,
            RoutingProvider provider,
            String externalId
    ) {
        this.id = Objects.requireNonNull(id);
        this.provider = Objects.requireNonNull(provider);
        this.externalId = Objects.requireNonNull(externalId);

        if (externalId.isBlank()) {
            throw new IllegalArgumentException("External id cannot be blank");
        }
    }
}