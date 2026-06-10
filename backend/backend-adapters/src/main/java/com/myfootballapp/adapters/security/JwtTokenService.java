package com.myfootballapp.adapters.security;

import com.myfootballapp.domain.model.User;
import com.myfootballapp.ports.out.TokenServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class JwtTokenService implements TokenServicePort {

    private final JwtProvider jwtProvider;

    @Override
    public String generateAccesToken(User user) {
        return jwtProvider.generateToken(user.getEmail());
    }

    @Override
    public String generateRefreshToken(User user) {
        return jwtProvider.generateToken(user.getEmail());
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
