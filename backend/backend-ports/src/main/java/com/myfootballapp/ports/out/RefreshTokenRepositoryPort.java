package com.myfootballapp.ports.out;


import com.myfootballapp.domain.model.RefreshToken;
import com.myfootballapp.domain.model.User;

import java.util.Optional;

public interface RefreshTokenRepositoryPort {

    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findByToken(String token);
}
