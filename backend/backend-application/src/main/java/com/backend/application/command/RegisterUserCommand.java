package com.backend.application.command;

import lombok.Builder;

@Builder
public record RegisterUserCommand (
        String username,
        String password,
        String firstName,
        String lastName,
        String email
) {}

