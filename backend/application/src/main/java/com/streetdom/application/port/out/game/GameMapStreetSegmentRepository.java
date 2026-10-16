package com.streetdom.application.port.out.game;

import com.streetdom.domain.model.game.GameMapStreetSegment;

import java.util.List;

public interface GameMapStreetSegmentRepository {
    void saveAll(List<GameMapStreetSegment> gameMapStreetSegments);
}
