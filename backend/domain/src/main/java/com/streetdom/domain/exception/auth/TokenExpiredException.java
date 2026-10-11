package com.streetdom.domain.exception.auth;

import com.streetdom.domain.exception.DomainException;

public class TokenExpiredException extends DomainException {

    public TokenExpiredException() {
        super("The token has expired");
    }
}
