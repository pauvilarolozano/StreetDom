package com.backend.application.port.out;

public interface TokenServicePort {

    String generateAccessToken(String username, String email);
    String generateRefreshToken(String username);
    String extractUsername(String token);
    boolean isValidAccessToken(String token);
}
