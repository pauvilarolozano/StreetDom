package com.backend.application.result;

public record TokensResult (
        String accessToken,
        String refreshToken
) {}