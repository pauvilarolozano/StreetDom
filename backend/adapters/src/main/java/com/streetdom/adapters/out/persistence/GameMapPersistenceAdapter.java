package com.streetdom.adapters.out.persistence;

import com.streetdom.adapters.out.persistence.entity.GameMapEntity;
import com.streetdom.adapters.out.persistence.jpa.GameMapJpaRepository;
import com.streetdom.adapters.out.persistence.mapper.GameMapMapper;
import com.streetdom.application.port.out.GameMapRepository;
import com.streetdom.domain.model.GameMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GameMapPersistenceAdapter implements GameMapRepository {

    private final GameMapJpaRepository repository;
    private final GameMapMapper mapper;

    @Override
    public void save(GameMap gameMap) {
        repository.save(mapper.toEntity(gameMap));
    }

    @Override
    public Optional<GameMap> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}













