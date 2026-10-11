package com.streetdom.domain.exception.game;


import com.streetdom.domain.exception.DomainException;

import java.util.UUID;

public class GameMapNotFoundException extends DomainException {
    public GameMapNotFoundException(UUID gameMapId) {
        super("GameMap "+gameMapId+" not found");
    }
}
