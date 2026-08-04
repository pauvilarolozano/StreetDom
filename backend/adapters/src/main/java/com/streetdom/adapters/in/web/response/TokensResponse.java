package com.streetdom.adapters.in.web.response;

public record TokensResponse (
            String accessToken,
            String refreshToken
) {}
