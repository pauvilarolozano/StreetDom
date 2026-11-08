package com.streetdom.application.interaction.result;

import com.streetdom.domain.model.game.GameStreetSegment;

import java.util.List;

public record InteractionResult(
        List<GameStreetSegment> changedSegments
) {}
