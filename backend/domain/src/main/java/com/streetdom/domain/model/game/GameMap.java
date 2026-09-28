package com.streetdom.domain.model.game;

import lombok.Builder;
import lombok.Value;
import java.util.UUID;

@Builder
@Value
public class GameMap {
    UUID id;
    String name;
}
