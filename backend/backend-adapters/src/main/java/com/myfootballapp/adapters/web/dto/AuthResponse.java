package com.myfootballapp.adapters.web.dto;

public record AuthResponse (
        UserResponse user,
        TokensResponse tokens
) {}
