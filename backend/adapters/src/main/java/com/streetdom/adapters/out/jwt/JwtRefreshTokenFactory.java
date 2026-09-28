package com.streetdom.adapters.out.jwt;

import com.streetdom.application.port.out.RefreshTokenFactory;
import com.streetdom.application.port.out.TokenHasher;
import com.streetdom.application.port.out.result.RefreshTokenBundle;
import com.streetdom.domain.model.identity.RefreshToken;
import com.streetdom.domain.model.identity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtRefreshTokenFactory implements RefreshTokenFactory {

    private final JwtTokenSigner jwtTokenSigner;
    private final JwtConfigProperties jwtConfigProperties;
    private final TokenHasher hasher;
    private final Clock clock;

    @Override
    public RefreshTokenBundle create(User user) {

        Instant sessionMaxExpiration = clock.instant()
                .plus(Duration.ofMillis(jwtConfigProperties.getSessionMaxExpiration()));

        return generateBundle(user, sessionMaxExpiration);
    }

    @Override
    public RefreshTokenBundle rotate(RefreshToken currentToken) {
        return generateBundle(currentToken.getUser(), currentToken.getSessionMaxUntil());
    }

    private RefreshTokenBundle generateBundle(User user, Instant sessionMaxUntil) {
        String newTokenRaw = jwtTokenSigner.generateRefreshToken(user.getUsername());
        Instant expiration = jwtTokenSigner.extractExpiration(newTokenRaw);

        RefreshToken newToken = RefreshToken.builder()
                .tokenHash(hasher.hash(newTokenRaw))
                .expiresAt(expiration)
                .sessionMaxUntil(sessionMaxUntil)
                .user(user)
                .revoked(false)
                .build();

        return new RefreshTokenBundle(newToken, newTokenRaw);
    }
}
