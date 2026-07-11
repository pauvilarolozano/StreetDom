package com.backend.adapters.in.web.mapper;

import com.backend.adapters.in.web.request.LoginUserRequest;
import com.backend.adapters.in.web.request.RegisterUserRequest;
import com.backend.adapters.in.web.response.AuthResponse;
import com.backend.adapters.in.web.response.TokensResponse;
import com.backend.adapters.in.web.response.UserResponse;
import com.backend.application.command.LoginUserCommand;
import com.backend.application.command.RegisterUserCommand;
import com.backend.application.result.AuthResult;
import com.backend.application.result.TokensResult;
import org.springframework.stereotype.Component;

@Component
public class AuthWebMapper {

    public RegisterUserCommand toCommand(RegisterUserRequest request) {
        return RegisterUserCommand.builder()
                .username(request.username())
                .password(request.password())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
    }

    public LoginUserCommand toCommand(LoginUserRequest request) {

        return new LoginUserCommand(request.username(), request.password());
    }

    public AuthResponse toResponse(AuthResult result) {

        //UserResponse can return more fields, but at the moment it is enough
        UserResponse userInfo = UserResponse.builder()
                                    .id(result.user().getId())
                                    .username(result.user().getUsername())
                                    .build();

        TokensResponse tokensResponse = new TokensResponse(
                result.tokens().accessToken(),
                result.tokens().refreshToken()
        );

        return new AuthResponse(userInfo,tokensResponse);
    }


    public TokensResponse toResponse(TokensResult tokensResult) {

        return new TokensResponse(
                tokensResult.accessToken(),
                tokensResult.refreshToken()
        );
    }
}
