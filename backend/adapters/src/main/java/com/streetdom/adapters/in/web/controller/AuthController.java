package com.streetdom.adapters.in.web.controller;

import com.streetdom.adapters.in.web.request.RefreshTokenRequest;
import com.streetdom.adapters.in.web.response.AuthSessionResponse;
import com.streetdom.adapters.in.web.request.LoginUserRequest;
import com.streetdom.adapters.in.web.request.RegisterUserRequest;
import com.streetdom.adapters.in.web.response.TokensResponse;
import com.streetdom.adapters.in.web.mapper.AuthWebMapper;
import com.streetdom.adapters.in.web.response.UserResponse;
import com.streetdom.application.command.LoginUserCommand;
import com.streetdom.application.command.RegisterUserCommand;
import com.streetdom.application.port.in.AuthUseCase;
import com.streetdom.application.port.in.result.SessionResult;
import com.streetdom.application.port.in.result.TokensResult;
import com.streetdom.application.port.in.result.UserResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthUseCase authUseCase;
    private final AuthWebMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<AuthSessionResponse> login(@Valid @RequestBody LoginUserRequest request) {

        LoginUserCommand command = mapper.toCommand(request);
        SessionResult result = authUseCase.login(command);
        AuthSessionResponse response = mapper.toResponse(result);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthSessionResponse> register(@Valid @RequestBody RegisterUserRequest request) {

        RegisterUserCommand command = mapper.toCommand(request);
        SessionResult result = authUseCase.register(command);
        AuthSessionResponse response = mapper.toResponse(result);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshTokenRequest refreshToken) {
        authUseCase.logout(refreshToken.token());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokensResponse> refresh(@RequestBody RefreshTokenRequest request) {

        TokensResult result = authUseCase.refresh(request.token());
        TokensResponse response = mapper.toResponse(result);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(Authentication authentication) {

        UserResult result = authUseCase.me(authentication.getName());
        UserResponse response = mapper.toResponse(result);

        return ResponseEntity.ok(response);
    }

}
