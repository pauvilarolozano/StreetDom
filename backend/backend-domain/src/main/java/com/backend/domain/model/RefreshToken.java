package com.backend.domain.model;

import com.backend.domain.exception.RefreshTokenExpiredException;
import com.backend.domain.exception.RefreshTokenReuseException;
import com.backend.domain.exception.SessionExpiredException;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.Instant;

@Builder
@Getter
public class RefreshToken {

    String value;
    User user;
    Instant expiresAt;
    Instant sessionMaxUntil;
    boolean revoked;

    public static RefreshToken create(String token, User user) {
        return RefreshToken.builder()
                .value(token)
                .user(user)
                .expiresAt(Instant.now().plus(Duration.ofDays(7)))
                .sessionMaxUntil(Instant.now().plus(Duration.ofDays(7)))
                .revoked(false)
                .build();
    }

    public static RefreshToken createFromRotation(String token, User user, Instant sessionMaxUntil) {
        return RefreshToken.builder()
                .value(token)
                .user(user)
                .expiresAt(Instant.now().plus(Duration.ofDays(7)))
                .sessionMaxUntil(sessionMaxUntil)
                .revoked(false)
                .build();
    }

    public void revoke() {
        this.revoked = true;
    }

    public void validate() {
        Instant now = Instant.now();

        if (revoked) throw new RefreshTokenReuseException();
        if (now.isAfter(expiresAt)) throw new RefreshTokenExpiredException();
        if (now.isAfter(sessionMaxUntil)) throw new SessionExpiredException();
    }
}
