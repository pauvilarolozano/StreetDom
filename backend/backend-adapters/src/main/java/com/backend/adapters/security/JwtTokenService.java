package com.backend.adapters.security;

import com.backend.ports.out.TokenServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class JwtTokenService implements TokenServicePort {

    private final JwtProvider jwtProvider;

    @Override
    public String generateAccessToken(String username, String email) {
        return jwtProvider.generateAccesToken(username, email);
    }

    @Override
    public String generateRefreshToken(String username) {
        return jwtProvider.generateRefreshToken(username);
    }

    @Override
    public String extractUsername(String token) {
        return jwtProvider.extractUsername(token);
    }

    @Override
    public boolean isValid(String token) {
        return jwtProvider.validate(token);
    }
}
