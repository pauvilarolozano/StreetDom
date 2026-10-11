package com.streetdom.domain.exception.auth;

import com.streetdom.domain.exception.DomainException;

public class RefreshTokenReuseException extends DomainException {

    public RefreshTokenReuseException() {
        super("Refresh token is revoked");
    }
}
