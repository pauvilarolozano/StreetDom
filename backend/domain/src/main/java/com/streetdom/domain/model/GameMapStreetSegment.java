package com.streetdom.domain.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class GameMapStreetSegment {
    GameMap gameMap;
    StreetSegment streetSegment;
    StreetSegmentState state;
}
