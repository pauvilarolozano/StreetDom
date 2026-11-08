package com.streetdom.application.interaction;

import com.streetdom.application.interaction.result.InteractionResult;
import com.streetdom.domain.model.game.GameStreetSegment;
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
            List<GameStreetSegment> segments,
            InteractionType interactionType
    ) {
        List<GameStreetSegment> changedSegments = switch (interactionType) {
            case MOVEMENT -> applyMovement(player, segments);
        };

        return new InteractionResult(changedSegments);
    }

    private List<GameStreetSegment> applyMovement(Player player, List<GameStreetSegment> segments) {
        return switch (player.getRole()) {
            case INFECTED -> applyToSegments(segments, GameStreetSegment::infect);
            case MEDIC -> applyToSegments(segments, GameStreetSegment::disinfect);
            case SURVIVOR, UNASSIGNED -> List.of();
        };
    }

    private List<GameStreetSegment> applyToSegments(
            List<GameStreetSegment> segments,
            Consumer<GameStreetSegment> interaction
    ) {
        List<GameStreetSegment> changedSegments = new ArrayList<>();

        for (GameStreetSegment segment : segments) {
            StreetSegmentState previousState = segment.getState();
            interaction.accept(segment);

            if (segment.getState() != previousState) {
                changedSegments.add(segment);
            }
        }

        return changedSegments;
    }
}
