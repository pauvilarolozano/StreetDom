package com.streetdom.domain.model;

import lombok.Builder;

import java.util.UUID;

@Builder
public class Player {
    private UUID id;
    private String username; //TODO: username deberia ser solo de Player? yo creo que si. User es solo auth
    private PlayerRole playerRol;
    private Location location;
}


