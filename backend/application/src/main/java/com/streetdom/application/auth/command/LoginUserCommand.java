package com.streetdom.application.auth.command;

public record LoginUserCommand (
        String username,
        String password
) {}
