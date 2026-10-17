package com.streetdom.domain.exception.game;

import com.streetdom.domain.exception.DomainException;

import java.util.UUID;

public class PlayerAlreadyInActiveGameException extends DomainException {
    public PlayerAlreadyInActiveGameException(UUID userId, UUID gameId) {
        super("User " + userId + " already participates in game " + gameId + " in progress");
    }
}
