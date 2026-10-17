package com.streetdom.application.port.out.game;

import com.streetdom.domain.model.game.GameMapStreetSegment;

import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.UUID;

public interface GameMapStreetSegmentRepository {
    List<GameMapStreetSegment> findByStreetSegmentIdsAndGameMapId(Set<UUID> streetSegmentIds, UUID gameMapId);

    Optional<GameMapStreetSegment> findByStreetSegmentIdAndGameMapId(UUID streetSegmentId, UUID gameMapId);

    void saveAll(List<GameMapStreetSegment> gameMapStreetSegments);
}
