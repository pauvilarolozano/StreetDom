package com.backend.adapters.web.controller;

import com.backend.adapters.web.dto.AuthResponse;
import com.backend.adapters.web.dto.LoginUserRequest;
import com.backend.adapters.web.dto.RegisterUserRequest;
import com.backend.adapters.web.dto.TokensResponse;
import com.backend.adapters.web.mapper.AuthWebMapper;
import com.backend.ports.in.dto.LoginUserCommand;
import com.backend.ports.in.dto.RegisterUserCommand;
import com.backend.ports.in.dto.AuthResult;
import com.backend.ports.in.dto.TokensResult;
import com.backend.ports.in.useCase.AuthUseCase;
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
