package com.streetdom.adapters.out.persistence.jpa;

import com.streetdom.adapters.out.persistence.entity.ZoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ZoneJpaRepository extends JpaRepository<ZoneEntity, UUID> {

    Optional<ZoneEntity> findByH3Index(String h3Index);
}
