package com.streetdom.application.game;

import com.streetdom.application.game.result.FinishGameResult;
import com.streetdom.application.game.result.PlayerResult;
import com.streetdom.application.game.result.StartGameResult;
import com.streetdom.application.game.result.CurrentGameResult;
import com.streetdom.application.game.result.GameSegmentsResult;
import com.streetdom.domain.model.game.Game;
import com.streetdom.domain.model.game.Player;
import org.springframework.stereotype.Component;

@Component
public class GameServiceMapper {

    public CurrentGameResult toCurrentGameResult(Game game, Player player, GameSegmentsResult gameSegments) {
        return new CurrentGameResult(
                game.getId(),
                game.getState(),
                game.getCreatedAt(),
                game.getStartedAt(),
                game.getFinishedAt(),
                toResult(player),
                gameSegments.segments()
        );
    }

    public StartGameResult toResult(Game game, Player player) {
        return new StartGameResult(
                game.getId(),
                game.getState(),
                game.getStartedAt(),
                toResult(player)
        );
    }

    public FinishGameResult toFinishGameResult(Game game) {
        return new FinishGameResult(game.getId(), game.getState(), game.getFinishedAt());
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
