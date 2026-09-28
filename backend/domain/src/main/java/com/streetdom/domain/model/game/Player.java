package com.streetdom.domain.model.game;

import java.util.UUID;

public class Player {
    private UUID id;
    private String username; //TODO: username deberia ser solo de Player? yo creo que si. User es solo auth
    private PlayerRole playerRol;
    private UUID gameId;
}


