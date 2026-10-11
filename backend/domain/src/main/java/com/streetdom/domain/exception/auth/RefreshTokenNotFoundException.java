package com.streetdom.domain.exception.auth;

import com.streetdom.domain.exception.DomainException;

public class RefreshTokenNotFoundException extends DomainException {
    public RefreshTokenNotFoundException() {
        super("Refresh token does not exist");
    }
}
