package com.streetdom.adapters.out.redis.matchmaking;

import com.streetdom.domain.model.geography.GeographicCell;
import com.streetdom.domain.model.geography.GpsPosition;
import com.streetdom.domain.model.geography.Location;
import com.streetdom.domain.model.matchmaking.MatchmakingEntry;
import org.springframework.stereotype.Component;

@Component
public class RedisMatchmakingMapper {

    public RedisMatchmakingEntry toRedis(MatchmakingEntry entry) {
        return new RedisMatchmakingEntry(
                entry.userId(),
                entry.gpsPosition().location().latitude(),
                entry.gpsPosition().location().longitude(),
                entry.gpsPosition().recordedAt(),
                entry.gpsPosition().accuracyMeters(),
                entry.cell().value(),
                entry.joinedAt()
        );
    }

    public MatchmakingEntry toDomain(RedisMatchmakingEntry entry) {
        GpsPosition gpsPosition = new GpsPosition(
                new Location(
                        entry.latitude(),
                        entry.longitude()
                ),
                entry.recordedAt(),
                entry.accuracyMeters()
        );

        return new MatchmakingEntry(
                entry.userId(),
                gpsPosition,
                new GeographicCell(entry.cellValue()),
                entry.joinedAt()
        );
    }
}