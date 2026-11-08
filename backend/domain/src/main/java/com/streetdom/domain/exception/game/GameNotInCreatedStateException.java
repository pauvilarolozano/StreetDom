package com.streetdom.domain.exception.game;

import com.streetdom.domain.exception.DomainException;

import java.util.UUID;

public class GameNotInCreatedStateException extends DomainException {
    public GameNotInCreatedStateException(UUID gameId) {
        super("Game " + gameId + " must be in CREATED state to be started");
    }
}
