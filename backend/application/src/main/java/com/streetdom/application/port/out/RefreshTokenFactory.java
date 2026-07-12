package com.streetdom.application.port.out;

import com.streetdom.domain.model.RefreshToken;
import com.streetdom.domain.model.User;

public interface RefreshTokenFactory {
    RefreshToken create(User user);
    RefreshToken rotate(RefreshToken refreshToken);
}
