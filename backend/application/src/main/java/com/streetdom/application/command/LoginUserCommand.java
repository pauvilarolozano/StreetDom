package com.streetdom.application.command;

public record LoginUserCommand (
        String username,
        String password
) {}
