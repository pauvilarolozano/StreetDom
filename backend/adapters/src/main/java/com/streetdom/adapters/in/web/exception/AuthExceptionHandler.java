package com.streetdom.adapters.in.web.exception;

import com.streetdom.adapters.in.web.response.ErrorResponse;
import com.streetdom.domain.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//TODO: check correctly where should be living each exception
@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler({
            RefreshTokenReuseException.class,
            RefreshTokenExpiredException.class,
            SessionExpiredException.class,
            RefreshTokenNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleSession(RuntimeException ex) {
        log.warn("Session error", ex);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("SESSION_EXPIRED","Please sign in again"));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("INVALID_CREDENTIALS","Invalid username or password"));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleRegisterUser() {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("USER_ALREADY_EXISTS","User already exists"));
    }

}
