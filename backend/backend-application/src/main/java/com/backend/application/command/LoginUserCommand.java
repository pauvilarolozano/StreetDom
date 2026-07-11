package com.backend.application.command;

public record LoginUserCommand (
        String username,
        String password
) {}
