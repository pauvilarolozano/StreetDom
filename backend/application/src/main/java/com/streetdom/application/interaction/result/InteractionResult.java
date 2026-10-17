package com.streetdom.application.interaction.result;

import com.streetdom.domain.model.game.GameMapStreetSegment;

import java.util.List;

public record InteractionResult(
        List<GameMapStreetSegment> changedSegments
) {}
