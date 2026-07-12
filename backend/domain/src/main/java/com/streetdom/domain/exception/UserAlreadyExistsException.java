package com.streetdom.domain.exception;

public class UserAlreadyExistsException extends DomainException {
    public UserAlreadyExistsException() {
        super("Username already exists");
    }
}
