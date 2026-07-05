package com.myfootballapp.ports.out;

import com.myfootballapp.domain.model.User;

public interface TokenServicePort {

    String generateAccessToken(String username, String email);
    String generateRefreshToken(String username);
    String extractUsername(String token);
    boolean isValid(String token);
}
