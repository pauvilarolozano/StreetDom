package com.streetdom.application.port.out.auth;

import com.streetdom.domain.model.auth.RefreshToken;
import com.streetdom.domain.model.auth.User;

public interface RefreshTokenFactory {

    RefreshTokenBundle create(User user);

    RefreshTokenBundle rotate(RefreshToken currentToken);
}
