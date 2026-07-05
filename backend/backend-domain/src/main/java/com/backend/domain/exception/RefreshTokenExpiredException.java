package com.backend.domain.exception;

public class RefreshTokenExpiredException extends DomainException{

    public RefreshTokenExpiredException () {
        super("Refresh token expired");
    }

}
