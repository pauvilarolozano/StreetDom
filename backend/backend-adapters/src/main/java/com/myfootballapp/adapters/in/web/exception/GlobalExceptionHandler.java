package com.myfootballapp.adapters.in.web.exception;

import com.myfootballapp.domain.exception.DomainException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity handleInvalidEmail(DomainException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(e.getMessage(), e.getCode()));
    }

}
