package com.streetdom.application.port.out.auth;

import com.streetdom.domain.model.auth.RefreshToken;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository {

    Optional<RefreshToken> findByTokenHash(String token);

    void save(RefreshToken refreshToken);

    void revokeAllByUserId(UUID userId);
}
