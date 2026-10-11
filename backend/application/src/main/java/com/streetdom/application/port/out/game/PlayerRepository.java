package com.streetdom.application.port.out.game;

import com.streetdom.domain.model.game.Player;

import java.util.List;
import java.util.UUID;

public interface PlayerRepository {
    void save(Player player);
    void saveAll(List<Player> players);

    List<Player> findByGameId(UUID gameId);

}
