package com.streetdom.domain.exception;

public class RefreshTokenNotFoundException extends DomainException {
    public RefreshTokenNotFoundException() {
        super("Refresh token does not exist");
    }
}
