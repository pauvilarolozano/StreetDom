package com.myfootballapp.application.mapper;


import com.myfootballapp.ports.in.command.RegisterUserCommand;
import com.myfootballapp.ports.in.result.AuthResult;
import com.myfootballapp.domain.model.User;

public class AuthMapper {

    public static User userToDomain(RegisterUserCommand userCommand) {

        return User.builder()
                .username(userCommand.getUsername())
                .password(userCommand.getPassword())
                .firstName(userCommand.getFirstName())
                .lastName(userCommand.getLastName())
                .email(userCommand.getEmail())
                .build();
    }

    public static AuthResult userToResult(User user) {
        return AuthResult.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
