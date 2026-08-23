package com.streetdom.application.port.in.result;

import java.util.UUID;

public record UserResult(
        UUID id,
        String username,
        String email) {
}
