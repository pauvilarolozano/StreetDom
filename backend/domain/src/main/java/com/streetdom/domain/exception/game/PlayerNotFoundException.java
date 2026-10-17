package com.streetdom.domain.exception.game;

import com.streetdom.domain.exception.DomainException;

import java.util.UUID;

public class PlayerNotFoundException extends DomainException {
    public PlayerNotFoundException(UUID userId, UUID gameId) {
        super("Player not found for user " + userId + " in game " + gameId);
    }
}
