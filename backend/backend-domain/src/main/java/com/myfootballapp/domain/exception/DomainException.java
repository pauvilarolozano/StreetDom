package com.myfootballapp.domain.exception;

public abstract class DomainException extends RuntimeException{

    public DomainException(String message) {
        super(message);
    }
}
