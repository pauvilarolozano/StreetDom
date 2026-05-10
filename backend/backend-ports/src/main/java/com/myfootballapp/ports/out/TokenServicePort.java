package com.myfootballapp.ports.out;

import com.myfootballapp.domain.model.User;

public interface TokenServicePort {

    String generateAccesToken(User user);
    String generateRefreshToken(User user);
    String extractEmail(String token);
    boolean isValid(String token);
}
