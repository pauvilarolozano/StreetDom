package com.streetdom.application.auth.command;

import lombok.Builder;

@Builder
public record RegisterUserCommand (
        String username,
        String email,
        String password
) {}

