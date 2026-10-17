package com.streetdom.application.interaction;

import com.streetdom.application.interaction.result.InteractionResult;
import com.streetdom.application.port.out.game.GameMapStreetSegmentRepository;
import com.streetdom.application.port.out.game.GameRepository;
import com.streetdom.application.port.out.game.PlayerRepository;
import com.streetdom.application.streetnetwork.matching.MapMatchingService;
import com.streetdom.domain.exception.game.GameNotInProgressException;
import com.streetdom.domain.exception.game.GameNotFoundException;
import com.streetdom.domain.exception.game.PlayerNotFoundException;
import com.streetdom.domain.model.game.Game;
import com.streetdom.domain.model.game.GameMapStreetSegment;
import com.streetdom.domain.model.game.Player;
import com.streetdom.domain.model.geography.Location;
import com.streetdom.domain.model.geography.GpsPosition;
import com.streetdom.domain.model.interaction.InteractionType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcessMovementService implements ProcessMovementUseCase {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final GameMapStreetSegmentRepository gameMapStreetSegmentRepository;

    private final MapMatchingService mapMatchingService;
    private final InteractionService interactionService;

    @Override
    @Transactional
    public InteractionResult process(ProcessMovementCommand command) {

        UUID userId = command.userId();
        UUID gameId = command.gameId();

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));

        if (!game.canProcessMovements()) {
            throw new GameNotInProgressException(gameId);
        }

        Player player = playerRepository.findPlayerById(userId, gameId)
                .orElseThrow(() -> new PlayerNotFoundException(userId, gameId));

        List<Location> locations = command.locations().stream()
                .map(GpsPosition::location)
                .toList();

        Set<UUID> streetSegmentIds = mapMatchingService.match(locations);

        if (streetSegmentIds.isEmpty()) {
            return new InteractionResult(List.of());
        }

        List<GameMapStreetSegment> gameMapStreetSegments = 
                gameMapStreetSegmentRepository.findByStreetSegmentIdsAndGameMapId(
                        streetSegmentIds,
                        game.getGameMapId()
                );

        InteractionResult interactionResult =
                interactionService.apply(player, gameMapStreetSegments, InteractionType.MOVEMENT);

        if (!interactionResult.changedSegments().isEmpty()) {
            gameMapStreetSegmentRepository.saveAll(interactionResult.changedSegments());
        }
        
        return interactionResult;
    }
}
