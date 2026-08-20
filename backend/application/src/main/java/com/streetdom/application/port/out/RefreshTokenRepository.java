package com.streetdom.application.port.out;

import com.streetdom.domain.model.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository {

    Optional<RefreshToken> findByTokenHash(String token);
    void save(RefreshToken refreshToken);
    void revokeAllByUserId(Long userId);
}
