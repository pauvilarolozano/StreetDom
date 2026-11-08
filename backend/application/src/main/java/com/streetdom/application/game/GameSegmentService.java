package com.streetdom.application.game;

import com.streetdom.application.game.query.GetGameSegmentsQuery;
import com.streetdom.application.game.result.GameSegmentsResult;
import com.streetdom.application.game.result.GameSegmentResult;
import com.streetdom.application.game.usecases.GameSegmentUseCase;
import com.streetdom.application.port.out.game.GameRepository;
import com.streetdom.application.port.out.game.PlayerRepository;
import com.streetdom.application.port.out.game.GameStreetSegmentRepository;
import com.streetdom.application.port.out.streetnetwork.StreetNetworkRepository;
import com.streetdom.domain.exception.game.GameNotFoundException;
import com.streetdom.domain.exception.game.PlayerNotFoundException;
import com.streetdom.domain.model.game.Game;
import com.streetdom.domain.model.game.GameStreetSegment;
import com.streetdom.domain.model.game.StreetSegmentState;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameSegmentService implements GameSegmentUseCase {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final GameStreetSegmentRepository gameStreetSegmentRepository;
    private final StreetNetworkRepository streetNetworkRepository;

    @Override
    @Transactional
    public GameSegmentsResult getGameSegments(GetGameSegmentsQuery query) {
        Game game = gameRepository.findById(query.gameId())
                .orElseThrow(() -> new GameNotFoundException(query.gameId()));

        playerRepository.findPlayerById(query.userId(), query.gameId())
                .orElseThrow(() -> new PlayerNotFoundException(query.userId(), query.gameId()));

        Map<UUID, StreetSegmentState> statesById = gameStreetSegmentRepository.findByGameId(game.getId())
                .stream()
                .collect(Collectors.toMap(GameStreetSegment::getStreetSegmentId, GameStreetSegment::getState));

        List<GameSegmentResult> gameSegments = streetNetworkRepository.findStreetSegmentsByIds(statesById.keySet())
                .stream()
                .map(segment -> new GameSegmentResult(
                        segment.getId(),
                        segment.getGeometry(),
                        statesById.get(segment.getId())
                ))
                .toList();

        return new GameSegmentsResult(game.getId(), gameSegments);
    }

}
