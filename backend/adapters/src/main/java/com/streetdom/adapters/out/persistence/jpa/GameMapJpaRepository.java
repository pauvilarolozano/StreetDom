package com.streetdom.adapters.out.persistence.jpa;

import com.streetdom.adapters.out.persistence.entity.GameMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface GameMapJpaRepository extends JpaRepository<GameMapEntity, UUID> {
}
