package com.streetdom.adapters.out.jwt;

import com.streetdom.application.port.out.RefreshTokenFactory;
import com.streetdom.application.port.out.TokenHasher;
import com.streetdom.application.port.out.result.RefreshTokenBundle;
import com.streetdom.domain.model.RefreshToken;
import com.streetdom.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtRefreshTokenFactory implements RefreshTokenFactory {

    private final JwtTokenSigner jwtTokenSigner;
    private final JwtConfigProperties jwtConfigProperties;
    private final TokenHasher hasher;

    @Override
    public RefreshTokenBundle create(User user) {
        // TODO: (Clean Code/Testing) no depender del reloj estático del sistema (Instant.now)

        Instant sessionMaxExpiration = Instant.now()
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
