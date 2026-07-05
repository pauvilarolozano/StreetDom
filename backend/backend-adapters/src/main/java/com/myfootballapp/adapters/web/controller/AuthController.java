package com.myfootballapp.adapters.web.controller;

import com.myfootballapp.adapters.web.dto.AuthResponse;
import com.myfootballapp.adapters.web.dto.LoginUserRequest;
import com.myfootballapp.adapters.web.dto.RegisterUserRequest;
import com.myfootballapp.adapters.web.dto.TokensResponse;
import com.myfootballapp.adapters.web.mapper.AuthWebMapper;
import com.myfootballapp.ports.in.dto.LoginUserCommand;
import com.myfootballapp.ports.in.dto.RegisterUserCommand;
import com.myfootballapp.ports.in.dto.AuthResult;
import com.myfootballapp.ports.in.dto.TokensResult;
import com.myfootballapp.ports.in.useCase.AuthUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authApplication;
    private final AuthWebMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterUserRequest request) {

        RegisterUserCommand command = mapper.toCommand(request);
        AuthResult result = authApplication.register(command);

        AuthResponse response = mapper.toResponse(result);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginUserRequest request) {

        LoginUserCommand command = mapper.toCommand(request);
        AuthResult result = authApplication.login(command);

        AuthResponse response = mapper.toResponse(result);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/refresh")
    public ResponseEntity<TokensResponse> refresh(@RequestBody String oldRefreshToken) {

        TokensResult tokensResult = authApplication.refresh(oldRefreshToken);
        TokensResponse response = mapper.toResponse(tokensResult);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    public ResponseEntity<AuthResponse> logout(@RequestBody String TODO) {
        return null;
    }
}
