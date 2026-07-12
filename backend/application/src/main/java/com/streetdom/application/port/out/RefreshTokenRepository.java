package com.streetdom.application.port.out;

import com.streetdom.domain.model.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository {

    void save(RefreshToken refreshToken);
    Optional<RefreshToken> findByToken(String token);
}
