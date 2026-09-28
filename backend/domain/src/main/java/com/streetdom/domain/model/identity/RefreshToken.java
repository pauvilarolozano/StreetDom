package com.streetdom.domain.model.identity;

import com.streetdom.domain.exception.TokenExpiredException;
import com.streetdom.domain.exception.RefreshTokenReuseException;
import com.streetdom.domain.exception.SessionExpiredException;
import lombok.Builder;
import lombok.Getter;
import java.time.Instant;
import java.util.Objects;

@Getter
public class RefreshToken {
    private final String tokenHash;
    private final Instant expiresAt;
    private final Instant sessionMaxUntil;
    private final User user;

    private boolean revoked;

    @Builder
    private RefreshToken(
            String tokenHash,
            Instant expiresAt,
            Instant sessionMaxUntil,
            User user,
            boolean revoked
    ) {
        this.tokenHash = Objects.requireNonNull(tokenHash);
        this.expiresAt = Objects.requireNonNull(expiresAt);
        this.sessionMaxUntil = Objects.requireNonNull(sessionMaxUntil);
        this.user = Objects.requireNonNull(user);
        this.revoked = revoked;

        if (tokenHash.isBlank()) {
            throw new IllegalArgumentException(
                    "Token hash cannot be blank"
            );
        }
    }


    public void revoke() {
        this.revoked = true;
    }

    public void validate(Instant now) {
        if (revoked) {
            throw new RefreshTokenReuseException();
        }

        if (!now.isBefore(sessionMaxUntil)) {
            throw new SessionExpiredException();
        }

        if (!now.isBefore(expiresAt)) {
            throw new TokenExpiredException();
        }
    }
}
