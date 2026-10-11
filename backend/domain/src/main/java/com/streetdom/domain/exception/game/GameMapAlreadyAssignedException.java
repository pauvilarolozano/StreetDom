package com.streetdom.domain.exception.game;

import com.streetdom.domain.exception.DomainException;

import java.util.UUID;

public class GameMapAlreadyAssignedException extends DomainException {
    public GameMapAlreadyAssignedException(UUID gameMapId) {
        super("GameMap "+gameMapId+" is already assigned to another game");
    }
}
