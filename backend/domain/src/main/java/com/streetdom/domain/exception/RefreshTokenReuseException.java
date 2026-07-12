package com.streetdom.domain.exception;

public class RefreshTokenReuseException extends DomainException{

    public RefreshTokenReuseException() {
        super("Refresh token is revoked");
    }
}
