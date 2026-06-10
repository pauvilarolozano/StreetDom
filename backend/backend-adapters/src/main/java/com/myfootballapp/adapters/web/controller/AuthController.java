package com.myfootballapp.adapters.web.controller;

import com.myfootballapp.adapters.web.dto.AuthResponse;
import com.myfootballapp.adapters.web.dto.LoginUserRequest;
import com.myfootballapp.adapters.web.dto.RegisterUserRequest;
import com.myfootballapp.adapters.web.mapper.AuthWebMapper;
import com.myfootballapp.ports.in.dto.LoginUserCommand;
import com.myfootballapp.ports.in.dto.RegisterUserCommand;
import com.myfootballapp.ports.in.dto.AuthResult;
import com.myfootballapp.ports.in.useCase.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCase authApplication;
    private final AuthWebMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterUserRequest request) {

        RegisterUserCommand command = mapper.toCommand(request);
        AuthResult result = authApplication.register(command);

        AuthResponse response = AuthResponse.builder()
                .user(mapper.toResponse(result.getUser()))
                .accesToken(result.getAccessToken())
                .refreshToken(result.getRefreshToken()).build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginUserRequest request) {

        LoginUserCommand command = mapper.toCommand(request);

        return null;
    }

}
