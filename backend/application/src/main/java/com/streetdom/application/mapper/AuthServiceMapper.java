package com.streetdom.application.mapper;

import com.streetdom.domain.model.User;
import com.streetdom.application.result.AuthResult;
import com.streetdom.application.command.RegisterUserCommand;
import com.streetdom.application.result.TokensResult;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceMapper {

    public User toDomain(RegisterUserCommand registerUserCommand, String passwordHash) {

        return User.builder()
                .username(registerUserCommand.username())
                .passwordHash(passwordHash)
                .firstName(registerUserCommand.firstName())
                .lastName(registerUserCommand.lastName())
                .email(registerUserCommand.email())
                .build();
    }

    public AuthResult toResult(User user, String accessToken, String refreshToken) {

        TokensResult tokensResult = new TokensResult(accessToken,refreshToken);

        return new AuthResult(user,tokensResult);
    }

}
