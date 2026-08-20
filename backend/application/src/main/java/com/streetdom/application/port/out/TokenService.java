package com.streetdom.application.port.out;

import java.time.Instant;

public interface TokenService {
    String generateAccessToken(String username, String email);

    String generateRefreshToken(String username);

    String extractUsername(String token);

    Instant extractExpiration(String token);

    void validateAccessToken(String token);

    void validateRefreshToken(String token);
}
