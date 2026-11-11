package com.streetdom.domain.exception.game;

import com.streetdom.domain.exception.DomainException;

import java.util.UUID;

public class GameCannotBeFinishedException extends DomainException {
    public GameCannotBeFinishedException(UUID gameId) {
        super("Game " + gameId + " must be in CREATED or IN_PROGRESS state to be finished");
    }
}
