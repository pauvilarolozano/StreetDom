package com.streetdom.application.auth.result;

import java.util.UUID;

public record UserResult(
        UUID id,
        String username,
        String email
) {}
