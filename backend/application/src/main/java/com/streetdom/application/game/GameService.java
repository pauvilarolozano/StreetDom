package com.streetdom.application.game;

import com.streetdom.application.game.command.CreateGameCommand;
import com.streetdom.application.game.command.StartGameCommand;
import com.streetdom.application.game.result.CreateGameResult;
import com.streetdom.application.game.result.StartGameResult;
import com.streetdom.application.game.usecases.GameUseCase;
import com.streetdom.application.game.usecases.GameSegmentUseCase;
import com.streetdom.application.game.query.GetCurrentGameQuery;
import com.streetdom.application.game.query.GetGameSegmentsQuery;
import com.streetdom.application.game.result.CurrentGameResult;
import com.streetdom.application.game.result.GameSegmentsResult;
import com.streetdom.application.port.out.game.GameStreetSegmentRepository;
import com.streetdom.application.port.out.game.GameRepository;
import com.streetdom.application.port.out.game.PlayerRepository;
import com.streetdom.application.port.out.geolocation.GeoLocationProvider;
import com.streetdom.application.port.out.streetnetwork.StreetNetworkRepository;
import com.streetdom.domain.exception.game.EmptyGameAreaException;
import com.streetdom.domain.exception.game.GameNotFoundException;
import com.streetdom.domain.exception.game.GameNotInCreatedStateException;
import com.streetdom.domain.exception.game.PlayerNotFoundException;
import com.streetdom.domain.exception.game.PlayerAlreadyInActiveGameException;
import com.streetdom.domain.model.game.*;
import com.streetdom.domain.model.geography.GeographicArea;
import com.streetdom.domain.model.geography.GeographicCell;
import com.streetdom.domain.model.streetnetwork.StreetSegment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Clock;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService implements GameUseCase {

    private final GameRepository gameRepository;
    private final GameStreetSegmentRepository gameStreetSegmentRepository;
    private final PlayerRepository playerRepository;
    private final GeoLocationProvider geoLocationProvider;
    private final StreetNetworkRepository streetNetworkRepository;
    private final GameServiceMapper mapper;
    private final GameSegmentUseCase gameSegmentUseCase;
    private final Clock clock;
    
    //private final int MIN_PLAYERS = 6;

    @Override
    @Transactional
    public Optional<CurrentGameResult> getCurrentGame(GetCurrentGameQuery query) {
        return gameRepository.findLatestByUserIdAndState(query.userId(), GameState.IN_PROGRESS)
                .or(() -> gameRepository.findLatestByUserIdAndState(query.userId(), GameState.CREATED))
                .map(game -> {
                    Player player = playerRepository.findPlayerById(query.userId(), game.getId())
                            .orElseThrow(() -> new PlayerNotFoundException(query.userId(), game.getId()));

                    GameSegmentsResult gameSegments = gameSegmentUseCase.getGameSegments(new GetGameSegmentsQuery(query.userId(), game.getId()));
                    return mapper.toCurrentGameResult(game, player, gameSegments);
                });
    }

    @Override
    @Transactional
    public CreateGameResult createGame(CreateGameCommand command) {

        playerRepository.findGameIdByUserIdAndGameState(command.userId(), GameState.IN_PROGRESS)
                .ifPresent(gameId -> {
                    throw new PlayerAlreadyInActiveGameException(command.userId(), gameId);
                });

        GeographicCell originCell = geoLocationProvider.getCell(command.gpsPosition().location());
        Set<GeographicCell> playableCells = geoLocationProvider.getCellsWithinDistance(originCell,2);

        GeographicArea gameArea = geoLocationProvider.getArea(playableCells);
        List<StreetSegment> streetSegments = streetNetworkRepository.findStreetSegmentsInArea(gameArea);

        if (streetSegments.isEmpty()) {
            throw new EmptyGameAreaException();
        }

        Instant now = clock.instant();
        Game game = Game.create(now);
        gameRepository.save(game);

        List<GameStreetSegment> gameStreetSegments = streetSegments.stream()
                .map(streetSegment -> GameStreetSegment.create(game.getId(), streetSegment.getId()))
                .toList();
        gameStreetSegmentRepository.saveAll(gameStreetSegments);

        Player player = Player.create(command.userId(), game.getId(), now);
        playerRepository.save(player);

        return new CreateGameResult(game.getId(), game.getState());
    }


    @Override
    @Transactional
    public StartGameResult startGame(StartGameCommand command) {
        UUID gameId = command.gameId();
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));

        if (game.getState() != GameState.CREATED) {
            throw new GameNotInCreatedStateException(gameId);
        }

        Player player = playerRepository.findPlayerById(command.userId(), gameId)
                .orElseThrow(() -> new PlayerNotFoundException(command.userId(), gameId));

        playerRepository.findGameIdByUserIdAndGameState(command.userId(), GameState.IN_PROGRESS)
                .ifPresent(activeGameId -> {
                    throw new PlayerAlreadyInActiveGameException(command.userId(), activeGameId);
                });

        player.assignInfectedRole();  //TODO modificar esta asignacion en el futuro
        playerRepository.save(player);

        game.start(clock.instant());
        gameRepository.save(game);

        return mapper.toResult(game, player);
    }
}
