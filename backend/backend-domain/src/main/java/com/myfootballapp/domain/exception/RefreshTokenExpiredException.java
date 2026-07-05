package com.myfootballapp.domain.exception;

public class RefreshTokenExpiredException extends DomainException{

    public RefreshTokenExpiredException () {
        super("Refresh token expired");
    }

}
