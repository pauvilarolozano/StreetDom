package com.myfootballapp.application.mapper;
import com.myfootballapp.domain.model.User;
import com.myfootballapp.ports.in.dto.AuthResult;
import com.myfootballapp.ports.in.dto.RegisterUserCommand;
import com.myfootballapp.ports.in.dto.TokensResult;
import org.springframework.stereotype.Component;

@Component
public class AuthApplicationMapper {

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
