package com.streetdom.domain.exception.auth;

import com.streetdom.domain.exception.DomainException;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException(String username) {
        super("Username "+username+" not found");
    }
}
