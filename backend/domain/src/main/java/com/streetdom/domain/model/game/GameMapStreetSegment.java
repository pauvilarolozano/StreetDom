package com.streetdom.domain.model.game;

import com.streetdom.domain.model.geography.StreetSegment;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class GameMapStreetSegment {
    GameMap gameMap;
    StreetSegment streetSegment;
    StreetSegmentState state;
}
