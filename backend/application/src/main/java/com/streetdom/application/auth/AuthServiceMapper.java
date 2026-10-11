package com.streetdom.application.auth;

import com.streetdom.domain.model.auth.User;
import com.streetdom.application.auth.result.SessionResult;
import com.streetdom.application.auth.result.UserResult;
import com.streetdom.application.auth.result.TokensResult;
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
