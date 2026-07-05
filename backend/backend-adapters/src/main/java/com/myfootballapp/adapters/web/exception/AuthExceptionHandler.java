package com.myfootballapp.adapters.web.exception;

import com.myfootballapp.adapters.web.dto.ErrorResponse;
import com.myfootballapp.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler({
            RefreshTokenReuseException.class,
            RefreshTokenExpiredException.class,
            SessionExpiredException.class,
            RefreshTokenNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleSession() {
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
