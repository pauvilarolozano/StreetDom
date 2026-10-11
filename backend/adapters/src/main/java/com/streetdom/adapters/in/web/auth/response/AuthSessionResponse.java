package com.streetdom.adapters.in.web.auth.response;

public record AuthSessionResponse(
        UserResponse user,
        TokensResponse tokens
) {}
