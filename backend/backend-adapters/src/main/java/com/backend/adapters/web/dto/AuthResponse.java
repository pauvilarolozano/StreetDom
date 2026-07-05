package com.backend.adapters.web.dto;

public record AuthResponse (
        UserResponse user,
        TokensResponse tokens
) {}
