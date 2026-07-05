package com.backend.ports.in.dto;

public record LoginUserCommand (
        String username,
        String password
) {}
