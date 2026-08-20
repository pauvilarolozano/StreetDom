package com.streetdom.application.port.out;

import com.streetdom.application.port.out.result.RefreshTokenBundle;
import com.streetdom.domain.model.RefreshToken;
import com.streetdom.domain.model.User;

public interface RefreshTokenFactory {

    RefreshTokenBundle create(User user);

    RefreshTokenBundle rotate(RefreshToken currentToken);
}
