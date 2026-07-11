package com.backend.application.port.out;

import com.backend.domain.model.RefreshToken;
import com.backend.domain.model.User;

public interface RefreshTokenFactory {
    RefreshToken create(User user);
    RefreshToken rotate(RefreshToken refreshToken);
}
