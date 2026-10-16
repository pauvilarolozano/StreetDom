package com.streetdom.domain.exception.game;

import com.streetdom.domain.exception.DomainException;

public class EmptyGameAreaException extends DomainException {
    public EmptyGameAreaException() {
        super("The game area contains no street segments");
    }
}
