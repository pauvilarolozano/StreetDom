package com.backend.application.port.out;


import com.backend.domain.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {

    void save(RefreshToken refreshToken);
    Optional<RefreshToken> findByToken(String token);
}
