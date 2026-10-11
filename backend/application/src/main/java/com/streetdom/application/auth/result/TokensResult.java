package com.streetdom.application.auth.result;

public record TokensResult (
        String accessToken,
        String refreshToken
) {}