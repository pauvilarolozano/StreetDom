package com.myfootballapp.ports.in.dto;

public record LoginUserCommand (
        String username,
        String password
) {}
