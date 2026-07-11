package com.backend.adapters.in.web.response;

public record AuthResponse (
        UserResponse user,
        TokensResponse tokens
) {}
