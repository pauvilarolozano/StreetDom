package com.streetdom.domain.exception;

public class TokenExpiredException extends DomainException{

    public TokenExpiredException() {
        super("The token has expired");
    }
}
