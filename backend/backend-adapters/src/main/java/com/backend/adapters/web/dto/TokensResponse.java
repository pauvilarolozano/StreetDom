package com.backend.adapters.web.dto;

public record TokensResponse (
        String accessToken,
        String refreshToken
) {}
