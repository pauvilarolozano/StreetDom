package com.backend.adapters.in.web.request;

import jakarta.validation.constraints.NotBlank;

public record LoginUserRequest (
        @NotBlank String username,
        @NotBlank String password
) {}
