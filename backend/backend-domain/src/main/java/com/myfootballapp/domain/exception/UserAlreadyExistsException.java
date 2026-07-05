package com.myfootballapp.domain.exception;

public class UserAlreadyExistsException extends DomainException {
    public UserAlreadyExistsException() {
        super("Username already exists");
    }
}
