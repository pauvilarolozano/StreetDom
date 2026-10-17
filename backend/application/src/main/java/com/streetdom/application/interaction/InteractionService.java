package com.streetdom.application.interaction;

import com.streetdom.application.interaction.result.InteractionResult;
import com.streetdom.domain.model.game.GameMapStreetSegment;
import com.streetdom.domain.model.game.Player;
import com.streetdom.domain.model.game.StreetSegmentState;
import com.streetdom.domain.model.interaction.InteractionType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class InteractionService {

    public InteractionResult apply(
            Player player,
            List<GameMapStreetSegment> segments,
            InteractionType interactionType
    ) {
        List<GameMapStreetSegment> changedSegments = switch (interactionType) {
            case MOVEMENT -> applyMovement(player, segments);
        };

        return new InteractionResult(changedSegments);
    }

    private List<GameMapStreetSegment> applyMovement(Player player, List<GameMapStreetSegment> segments) {
        return switch (player.getRole()) {
            case INFECTED -> applyToSegments(segments, GameMapStreetSegment::infect);
            case MEDIC -> applyToSegments(segments, GameMapStreetSegment::disinfect);
            case SURVIVOR, UNASSIGNED -> List.of();
        };
    }

    private List<GameMapStreetSegment> applyToSegments(
            List<GameMapStreetSegment> segments,
            Consumer<GameMapStreetSegment> interaction
    ) {
        List<GameMapStreetSegment> changedSegments = new ArrayList<>();

        for (GameMapStreetSegment segment : segments) {
            StreetSegmentState previousState = segment.getState();
            interaction.accept(segment);

            if (segment.getState() != previousState) {
                changedSegments.add(segment);
            }
        }

        return changedSegments;
    }
}
