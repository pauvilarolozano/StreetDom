package com.streetdom.application.port.out.game;

import com.streetdom.domain.model.game.GameStreetSegment;

import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.UUID;

public interface GameStreetSegmentRepository {
    List<GameStreetSegment> findByGameId(UUID gameId);

    List<GameStreetSegment> findByStreetSegmentIdsAndGameId(Set<UUID> streetSegmentIds, UUID gameId);

    Optional<GameStreetSegment> findByStreetSegmentIdAndGameId(UUID streetSegmentId, UUID gameId);

    void saveAll(List<GameStreetSegment> gameStreetSegments);
}
