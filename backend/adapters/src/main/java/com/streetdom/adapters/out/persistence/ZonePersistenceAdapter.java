package com.streetdom.adapters.out.persistence;

import com.streetdom.adapters.out.persistence.jpa.ZoneJpaRepository;
import com.streetdom.adapters.out.persistence.mapper.ZoneMapper;
import com.streetdom.application.port.out.ZoneRepository;
import com.streetdom.domain.model.Zone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ZonePersistenceAdapter implements ZoneRepository {

    private final ZoneJpaRepository zoneJpaRepository;
    private final ZoneMapper zoneMapper;

    @Override
    public Optional<Zone> findById(UUID id) {
        return zoneJpaRepository.findById(id).map(zoneMapper::toDomain);
    }

    @Override
    public Optional<Zone> findByH3Index(String h3Index) {
        return zoneJpaRepository.findByH3Index(h3Index).map(zoneMapper::toDomain);
    }

    @Override
    public void save(Zone zone) {
        zoneJpaRepository.save(zoneMapper.toEntity(zone));
    }
}
