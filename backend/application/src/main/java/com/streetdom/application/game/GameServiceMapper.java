package com.streetdom.application.game;

import com.streetdom.application.game.result.PlayerResult;
import com.streetdom.application.game.result.StartGameResult;
import com.streetdom.domain.model.game.Game;
import com.streetdom.domain.model.game.Player;
import org.springframework.stereotype.Component;

@Component
public class GameServiceMapper {

    public StartGameResult toResult(Game game, Player player) {
        return new StartGameResult(
                game.getId(),
                game.getGameMapId(),
                game.getState(),
                game.getStartedAt(),
                toResult(player)
        );
    }

    public PlayerResult toResult(Player player) {
        return new PlayerResult(
                player.getUserId(),
                player.getGameId(),
                player.getRole(),
                player.getJoinedAt()
        );
    }
}
