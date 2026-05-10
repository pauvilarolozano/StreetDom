package com.myfootballapp.adapters.web.controller;

import com.myfootballapp.adapters.web.dto.LoginUserRequest;
import com.myfootballapp.adapters.web.dto.RegisterUserRequest;
import com.myfootballapp.adapters.web.mapper.AuthRequestMapper;
import com.myfootballapp.ports.in.command.LoginUserCommand;
import com.myfootballapp.ports.in.command.RegisterUserCommand;
import com.myfootballapp.ports.in.result.AuthResult;
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


    @GetMapping("/login")
    public ResponseEntity<AuthResult> login(@RequestBody LoginUserRequest request) {

        LoginUserCommand command = AuthRequestMapper.toCommand(request);

        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResult> register(@RequestBody RegisterUserRequest request) {

        RegisterUserCommand command = AuthRequestMapper.toCommand(request);
        AuthResult response = authApplication.register(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


}
