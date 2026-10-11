package com.streetdom.adapters.out.persistence.game;

import com.streetdom.application.port.out.game.GameMapRepository;
import com.streetdom.domain.model.game.GameMap;
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
    public boolean existsById(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<GameMap> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }
}













