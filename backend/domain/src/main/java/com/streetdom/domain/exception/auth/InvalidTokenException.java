package com.streetdom.domain.exception.auth;

import com.streetdom.domain.exception.DomainException;

public class InvalidTokenException extends DomainException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
