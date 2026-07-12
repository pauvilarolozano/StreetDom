package com.streetdom.domain.exception;

public class SessionExpiredException extends DomainException{

    public SessionExpiredException() {
        super("Session expired");
    }
}
