package com.backend.ports.in.dto;

public record TokensResult (
        String accessToken,
        String refreshToken
) {}