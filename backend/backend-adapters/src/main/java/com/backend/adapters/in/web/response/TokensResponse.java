package com.backend.adapters.in.web.response;

public record TokensResponse (
        String accessToken,
        String refreshToken
) {}
