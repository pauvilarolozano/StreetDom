package com.streetdom.domain.model;

import com.streetdom.domain.exception.RefreshTokenExpiredException;
import com.streetdom.domain.exception.RefreshTokenReuseException;
import com.streetdom.domain.exception.SessionExpiredException;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class RefreshToken {

    String token;
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
