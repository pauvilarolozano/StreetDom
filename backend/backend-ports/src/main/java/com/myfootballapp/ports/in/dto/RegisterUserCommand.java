package com.myfootballapp.ports.in.dto;

import lombok.Builder;

@Builder
public record RegisterUserCommand (
        String username,
        String password,
        String firstName,
        String lastName,
        String email
) {}

