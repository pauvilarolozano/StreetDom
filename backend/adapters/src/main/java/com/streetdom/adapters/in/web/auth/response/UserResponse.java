package com.streetdom.adapters.in.web.auth.response;

import lombok.Builder;
import java.util.UUID;

@Builder
public record UserResponse (
        UUID id,
        String username,
        String email
) {}
