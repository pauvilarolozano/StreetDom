package com.streetdom.domain.model;

import com.streetdom.domain.exception.TokenExpiredException;
import com.streetdom.domain.exception.RefreshTokenReuseException;
import com.streetdom.domain.exception.SessionExpiredException;
import lombok.Builder;
import lombok.Getter;
import java.time.Instant;

@Builder
@Getter
public class RefreshToken {
    String tokenHash;
    Instant expiresAt;
    Instant sessionMaxUntil;
    User user;
    boolean revoked;

    public void revoke() {
        this.revoked = true;
    }

    public void validate(Instant now) {
        if (revoked) throw new RefreshTokenReuseException();
        if (now.isAfter(expiresAt)) throw new TokenExpiredException();
        if (now.isAfter(sessionMaxUntil)) throw new SessionExpiredException();
    }
}
