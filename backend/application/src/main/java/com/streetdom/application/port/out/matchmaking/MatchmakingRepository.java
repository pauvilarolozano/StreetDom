package com.streetdom.application.port.out.matchmaking;

import com.streetdom.domain.model.matchmaking.MatchmakingEntry;
import com.streetdom.domain.model.geography.GeographicCell;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface MatchmakingRepository {
    Optional<MatchmakingEntry> findByUserId(UUID userId);
    List<MatchmakingEntry> findByCells(Set<GeographicCell> cells);

    void save(MatchmakingEntry entry);
    void delete(UUID userId);
}
