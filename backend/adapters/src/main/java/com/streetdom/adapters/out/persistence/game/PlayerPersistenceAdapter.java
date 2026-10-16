package com.streetdom.adapters.out.persistence.game;

import com.streetdom.adapters.out.persistence.game.entities.PlayerEntity;
import com.streetdom.adapters.out.persistence.game.jpa.PlayerJpaRepository;
import com.streetdom.adapters.out.persistence.game.mapper.PlayerMapper;
import com.streetdom.application.port.out.game.PlayerRepository;
import com.streetdom.domain.model.game.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PlayerPersistenceAdapter implements PlayerRepository {

    private final PlayerJpaRepository playerJpaRepository;
    private final PlayerMapper mapper;

    @Override
    public void save(Player player) {
        playerJpaRepository.save(mapper.toEntity(player));
    }

    @Override
    public void saveAll(List<Player> players) {
        List<PlayerEntity> entities = players.stream()
                .map(mapper::toEntity)
                .toList();

        playerJpaRepository.saveAll(entities);
    }

    @Override
    public List<Player> findByGameId(UUID gameId) {
        return playerJpaRepository.findAllByPlayerIdGameId(gameId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
