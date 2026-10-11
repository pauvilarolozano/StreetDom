package com.streetdom.adapters.in.web.auth.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank
        String token
) {}
