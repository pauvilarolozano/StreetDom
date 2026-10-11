package com.streetdom.domain.exception.game;

import com.streetdom.domain.exception.DomainException;

import java.util.UUID;

public class NotEnoughPlayersException extends DomainException {
    public NotEnoughPlayersException(UUID gameId, int minimumPlayers) {
        super("At least " + minimumPlayers + " players are required to start the game "+gameId);
    }
}
