package com.backend.domain.model;

import com.backend.domain.exception.RefreshTokenExpiredException;
import com.backend.domain.exception.RefreshTokenReuseException;
import com.backend.domain.exception.SessionExpiredException;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class RefreshToken {

    String value;
    User user;
    Instant expiresAt;
    Instant sessionMaxUntil;
    boolean revoked;

    public void revoke() {
        this.revoked = true;
    }

    public void validate(Instant now) {
        if (revoked) throw new RefreshTokenReuseException();
        if (now.isAfter(expiresAt)) throw new RefreshTokenExpiredException();
        if (now.isAfter(sessionMaxUntil)) throw new SessionExpiredException();
    }
}
