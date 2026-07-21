package com.streetdom.adapters.in.web.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegisterUserRequest (
        @NotBlank String username,
        @NotBlank @Email String email,
        @NotBlank String password
) {}
