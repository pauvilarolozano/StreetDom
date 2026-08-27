package com.streetdom.application.port.out;

import com.streetdom.domain.model.Zone;

import java.util.Optional;
import java.util.UUID;

public interface ZoneRepository {
    Optional<Zone> findById(UUID id);
    Optional<Zone> findByH3Index(String h3Index);
    void save(Zone zone);
}
