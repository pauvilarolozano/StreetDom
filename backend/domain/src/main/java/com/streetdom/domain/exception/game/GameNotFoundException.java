package com.streetdom.domain.exception.game;

import com.streetdom.domain.exception.DomainException;

import java.util.UUID;

public class GameNotFoundException extends DomainException {
    public GameNotFoundException(UUID gameId) {
        super("Game not found");
    }
}
