package com.streetdom.domain.exception.auth;

import com.streetdom.domain.exception.DomainException;

public class SessionExpiredException extends DomainException {

    public SessionExpiredException() {
        super("Session expired");
    }
}
