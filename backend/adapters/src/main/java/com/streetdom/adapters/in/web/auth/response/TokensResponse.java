package com.streetdom.adapters.in.web.auth.response;

public record TokensResponse (
            String accessToken,
            String refreshToken
) {}
