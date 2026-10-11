package com.streetdom.domain.exception.auth;

import com.streetdom.domain.exception.DomainException;

public class InvalidCredentialsException extends DomainException {
    public InvalidCredentialsException() {
        super("Invalid username or password");
    }
}
