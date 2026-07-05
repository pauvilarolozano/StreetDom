package com.myfootballapp.adapters.web.dto;

public record TokensResponse (
        String accessToken,
        String refreshToken
) {}
