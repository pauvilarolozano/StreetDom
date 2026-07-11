package com.backend.application.mapper;
import com.backend.domain.model.User;
import com.backend.application.result.AuthResult;
import com.backend.application.command.RegisterUserCommand;
import com.backend.application.result.TokensResult;
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
