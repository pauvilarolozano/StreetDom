package com.streetdom.adapters.in.web.controller;

import com.streetdom.adapters.in.web.request.RefreshTokenRequest;
import com.streetdom.adapters.in.web.response.AuthResponse;
import com.streetdom.adapters.in.web.request.LoginUserRequest;
import com.streetdom.adapters.in.web.request.RegisterUserRequest;
import com.streetdom.adapters.in.web.response.TokensResponse;
import com.streetdom.adapters.in.web.mapper.AuthWebMapper;
import com.streetdom.application.command.LoginUserCommand;
import com.streetdom.application.command.RegisterUserCommand;
import com.streetdom.application.port.in.AuthUseCase;
import com.streetdom.application.result.AuthResult;
import com.streetdom.application.result.TokensResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/auth")
public class AuthController {

    private final AuthUseCase authUseCase;
    private final AuthWebMapper mapper;

    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterUserRequest request) {

        RegisterUserCommand command = mapper.toCommand(request);
        AuthResult result = authUseCase.register(command);

        AuthResponse response = mapper.toResponse(result);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginUserRequest request) {

        LoginUserCommand command = mapper.toCommand(request);
        AuthResult result = authUseCase.login(command);

        AuthResponse response = mapper.toResponse(result);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<TokensResponse> refresh(@RequestBody RefreshTokenRequest oldRefreshToken) {

        TokensResult tokensResult = authUseCase.refresh(oldRefreshToken.token());
        TokensResponse response = mapper.toResponse(tokensResult);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<String> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("Autentificado: "+auth.getName());
    }

    public ResponseEntity<AuthResponse> logout(@RequestBody String TODO) {
        return null;
    }
}
