package com.streetdom.domain.exception.auth;

import com.streetdom.domain.exception.DomainException;

public class UserAlreadyExistsException extends DomainException {
    public UserAlreadyExistsException(String username) {
        super("Username "+username+" already exists");
    }
}
