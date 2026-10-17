package com.streetdom.application.port.out.game;

import com.streetdom.domain.model.game.Player;
import com.streetdom.domain.model.game.GameState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository {
    Optional<Player> findPlayerById(UUID userId, UUID gameId);
    Optional<UUID> findGameIdByUserIdAndGameState(UUID userId, GameState state);

    void save(Player player);
    void saveAll(List<Player> players);

    List<Player> findByGameId(UUID gameId);

}
