package com.myfootballapp.adapters.in.web.controller;

import com.myfootballapp.adapters.in.web.dto.UserRequest;
import com.myfootballapp.adapters.in.web.dto.UserResponse;
import com.myfootballapp.adapters.in.web.mapper.UserRequestMapper;
import com.myfootballapp.adapters.in.web.mapper.UserResponseMapper;
import com.myfootballapp.domain.model.User;
import com.myfootballapp.ports.in.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userApplication;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request) {

        User userToRegister = UserRequestMapper.toDomain(request);
        User userRegistered = userApplication.register(userToRegister);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UserResponseMapper.toResponse(userRegistered));
    }
}
