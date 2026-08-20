package com.streetdom.application.port.in.result;

public record TokensResult (
        String accessToken,
        String refreshToken
) {}