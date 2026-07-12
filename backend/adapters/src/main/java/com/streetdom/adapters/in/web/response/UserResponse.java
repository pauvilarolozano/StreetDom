package com.streetdom.adapters.in.web.response;

import lombok.Builder;

@Builder
public record UserResponse (
        Long id,
        String username,
        String firstName,
        String lastName,
        String email
) {}
