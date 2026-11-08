package com.streetdom.adapters.out.persistence.game;

import com.streetdom.adapters.out.persistence.game.jpa.GameJpaRepository;
import com.streetdom.adapters.out.persistence.game.mapper.GameMapper;
import com.streetdom.application.port.out.game.GameRepository;
import com.streetdom.domain.model.game.Game;
import com.streetdom.domain.model.game.GameState;
import org.springframework.data.domain.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GamePersistenceAdapter implements GameRepository {

    private final GameJpaRepository gameJpaRepository;
    private final GameMapper gameMapper;

    @Override
    public Optional<Game> findLatestByUserIdAndState(UUID userId, GameState state) {
        return gameJpaRepository.findByUserIdAndState(userId, state, PageRequest.of(0, 1))
                .stream()
                .findFirst()
                .map(gameMapper::toDomain);
    }

    @Override
    public Optional<Game> findById(UUID id) {
        return gameJpaRepository.findById(id).map(gameMapper::toDomain);
    }

    @Override
    public void save(Game game) {
        gameJpaRepository.save(gameMapper.toEntity(game));
    }

    @Override
    public boolean existsById(UUID id) {
        return gameJpaRepository.existsById(id);
    }

}
