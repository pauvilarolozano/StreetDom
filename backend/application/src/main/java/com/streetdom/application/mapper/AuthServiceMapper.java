package com.streetdom.application.mapper;

import com.streetdom.domain.model.User;
import com.streetdom.application.port.in.result.SessionResult;
import com.streetdom.application.port.in.result.UserResult;
import com.streetdom.application.port.in.result.TokensResult;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceMapper {

    public SessionResult toResult(User user, String accessToken, String refreshToken) {

        UserResult userResult = new UserResult(user.getId(), user.getUsername(), user.getEmail());
        TokensResult tokensResult = new TokensResult(accessToken, refreshToken);

        return new SessionResult(userResult, tokensResult);
    }

    public UserResult toResult(User user) {
        return new UserResult(user.getId(), user.getUsername(), user.getEmail());
    }

}
