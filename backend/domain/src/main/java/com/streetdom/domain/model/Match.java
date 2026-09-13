package com.streetdom.domain.model;

import lombok.Builder;
import java.util.List;
import java.util.UUID;

@Builder
public class Match {
    private UUID id;
    private GameMap map;
    private List<Player> players;

}
