package com.streetdom.application.game;

import com.streetdom.application.game.command.AddPlayerToGameCommand;
import com.streetdom.application.game.result.PlayerResult;
import com.streetdom.application.port.out.game.GameRepository;
import com.streetdom.application.port.out.game.PlayerRepository;
import com.streetdom.domain.exception.game.GameNotFoundException;
import com.streetdom.domain.model.game.Player;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerService implements PlayerUseCase {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final Clock clock;

    @Override
    @Transactional
    public PlayerResult joinGame(AddPlayerToGameCommand command) {

        UUID gameId = command.gameId();

        if (!gameRepository.existsById(gameId)) {
            throw new GameNotFoundException(gameId);
        }

        //TODO: Comprobar si player ya esta en una partida (no finalizada) o si se puede unir a la partida pedida

        Player newPlayer = Player.create(command.userId(), gameId, clock.instant());
        playerRepository.save(newPlayer);

        return new PlayerResult(
                newPlayer.getUserId(),
                newPlayer.getGameId(),
                newPlayer.getRole(),
                newPlayer.getJoinedAt()
        );
    }
}
