package com.myfootballapp.adapters.in.web.mapper;

import com.myfootballapp.adapters.in.web.dto.LoginUserRequest;
import com.myfootballapp.adapters.in.web.dto.RegisterUserRequest;
import com.myfootballapp.ports.in.command.LoginUserCommand;
import com.myfootballapp.ports.in.command.RegisterUserCommand;

public class AuthRequestMapper {


    public static LoginUserCommand toCommand(LoginUserRequest request) {
        return LoginUserCommand.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
    }

    public static RegisterUserCommand toCommand(RegisterUserRequest request) {
        return RegisterUserCommand.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
    }



}
