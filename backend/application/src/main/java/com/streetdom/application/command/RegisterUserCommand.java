package com.streetdom.application.command;

import lombok.Builder;

@Builder
public record RegisterUserCommand (
        String username,
        String email,
        String password
) {}

