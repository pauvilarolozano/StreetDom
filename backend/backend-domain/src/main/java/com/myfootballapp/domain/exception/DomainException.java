package com.myfootballapp.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DomainException extends RuntimeException{

    private final String code;

    public DomainException(String message, String code) {
        super(message);
        this.code = code;
    }
}
