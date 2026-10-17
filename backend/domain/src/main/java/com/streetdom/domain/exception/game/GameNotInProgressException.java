package com.streetdom.domain.exception.game;

import com.streetdom.domain.exception.DomainException;

import java.util.UUID;

public class GameNotInProgressException extends DomainException {
    public GameNotInProgressException(UUID gameId) {
        super("Game " + gameId + " is not in progress");
    }
}
