package com.streetdom.application.game;

import com.streetdom.application.game.command.CreateGameCommand;
import com.streetdom.application.game.command.StartGameCommand;
import com.streetdom.application.game.result.CreateGameResult;
import com.streetdom.application.game.result.StartGameResult;
import com.streetdom.application.port.out.game.GameMapRepository;
import com.streetdom.application.port.out.game.GameRepository;
import com.streetdom.application.port.out.game.PlayerRepository;
import com.streetdom.domain.exception.game.GameMapAlreadyAssignedException;
import com.streetdom.domain.exception.game.GameMapNotFoundException;
import com.streetdom.domain.exception.game.GameNotFoundException;
import com.streetdom.domain.exception.game.NotEnoughPlayersException;
import com.streetdom.domain.model.game.Game;
import com.streetdom.domain.model.game.Player;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService implements GameUseCase {

    private final GameRepository gameRepository;
    private final GameMapRepository gameMapRepository;
    private final PlayerRepository playerRepository;
    private final Clock clock;

    private final int MIN_PLAYERS = 6;

    @Override
    @Transactional
    public CreateGameResult createGame(CreateGameCommand command) {

        UUID gameMapId = command.gameMapId();

        if (!gameMapRepository.existsById(gameMapId)) {
            throw new GameMapNotFoundException(gameMapId);
        }

        if (gameRepository.existsByGameMapId(gameMapId)) {
            throw new GameMapAlreadyAssignedException(gameMapId);
        }

        Game newGame = Game.create(gameMapId);
        gameRepository.save(newGame);

        return new CreateGameResult(
                newGame.getId(),
                newGame.getGameMapId(),
                newGame.getState()
        );
    }

    @Override
    @Transactional
    public StartGameResult startGame(StartGameCommand command) {

        UUID gameId = command.gameId();
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));

        List<Player> players = new ArrayList<>(
                playerRepository.findByGameId(gameId)
        );

        if (players.size() < MIN_PLAYERS) {
            throw new NotEnoughPlayersException(gameId,MIN_PLAYERS);
        }

        Collections.shuffle(players);

        //TODO: cambiar forma de asignar en el futuro
        players.get(0).assignInfectedRole();
        players.get(1).assignMedicRole();

        for (int i = 2; i < players.size(); i++) {
            players.get(i).assignSurvivorRole();
        }

        //TODO: inicializar estado map GameMapStreetSegments y estados iniciales
        game.start(clock.instant());

        playerRepository.saveAll(players);
        gameRepository.save(game);

        return null;
    }
}
