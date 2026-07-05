package com.myfootballapp.ports.in.dto;

public record TokensResult (
        String accessToken,
        String refreshToken
) {}