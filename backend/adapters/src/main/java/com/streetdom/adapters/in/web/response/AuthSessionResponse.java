package com.streetdom.adapters.in.web.response;

public record AuthSessionResponse(
        UserResponse user,
        TokensResponse tokens
) {}
