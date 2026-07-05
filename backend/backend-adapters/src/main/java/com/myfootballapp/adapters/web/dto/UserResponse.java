package com.myfootballapp.adapters.web.dto;

import lombok.Builder;

@Builder
public record UserResponse (
        Long id,
        String username,
        String firstName,
        String lastName,
        String email
) {}
