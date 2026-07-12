package com.streetdom.application.result;

public record TokensResult (
        String accessToken,
        String refreshToken
) {}