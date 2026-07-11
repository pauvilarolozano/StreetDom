package com.backend.adapters.out.jwt;

import com.backend.application.port.out.RefreshTokenFactory;
import com.backend.domain.model.RefreshToken;
import com.backend.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtRefreshTokenFactory implements RefreshTokenFactory {

    private final JwtTokenService jwtTokenService;
    private final JwtConfigProperties jwtConfigProperties;

    @Override
    public RefreshToken create(User user) {

        String newRefreshTokenValue = jwtTokenService.generateRefreshToken(user.getUsername());
        Instant expiration = jwtTokenService.extractExpiration(newRefreshTokenValue);
        Instant sessionMaxExpiration =
                Instant.now().plus(Duration.ofMillis(jwtConfigProperties.getSessionMaxExpiration()));

        return createRefreshToken(newRefreshTokenValue,user,expiration,sessionMaxExpiration);
    }

    @Override
    public RefreshToken rotate(RefreshToken refreshToken) {

        User userFromToken = refreshToken.getUser();

        String newRefreshTokenValue = jwtTokenService.generateRefreshToken(refreshToken.getUser().getUsername());
        Instant expiration = jwtTokenService.extractExpiration(newRefreshTokenValue);

        return createRefreshToken(newRefreshTokenValue,userFromToken,expiration,refreshToken.getSessionMaxUntil());
    }

    private RefreshToken createRefreshToken(String value, User user, Instant expiresAt, Instant sessionMaxUntil) {
        return RefreshToken.builder()
                .value(value)
                .user(user)
                .expiresAt(expiresAt)
                .sessionMaxUntil(sessionMaxUntil)
                .revoked(false)
                .build();
    }
}
