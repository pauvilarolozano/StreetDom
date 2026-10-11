package com.streetdom.adapters.in.web.auth;

import com.streetdom.adapters.in.web.auth.request.LoginUserRequest;
import com.streetdom.adapters.in.web.auth.request.RegisterUserRequest;
import com.streetdom.adapters.in.web.auth.response.AuthSessionResponse;
import com.streetdom.adapters.in.web.auth.response.TokensResponse;
import com.streetdom.adapters.in.web.auth.response.UserResponse;
import com.streetdom.application.auth.command.LoginUserCommand;
import com.streetdom.application.auth.command.RegisterUserCommand;
import com.streetdom.application.auth.result.SessionResult;
import com.streetdom.application.auth.result.TokensResult;
import com.streetdom.application.auth.result.UserResult;
import org.springframework.stereotype.Component;

@Component
public class AuthWebMapper {

    public RegisterUserCommand toCommand(RegisterUserRequest request) {
        return RegisterUserCommand.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .build();
    }

    public LoginUserCommand toCommand(LoginUserRequest request) {

        return new LoginUserCommand(request.username(), request.password());
    }

    public AuthSessionResponse toResponse(SessionResult result) {

        // UserResponse can return more fields, but at the moment it is enough
        UserResponse user = toResponse(result.user());
        TokensResponse tokens = toResponse(result.tokens());

        return new AuthSessionResponse(user, tokens);
    }

    public UserResponse toResponse(UserResult user) {
        return UserResponse.builder()
                .id(user.id())
                .username(user.username())
                .email(user.email())
                .build();
    }

    public TokensResponse toResponse(TokensResult tokensResult) {
        return new TokensResponse(
                tokensResult.accessToken(),
                tokensResult.refreshToken());
    }
}
