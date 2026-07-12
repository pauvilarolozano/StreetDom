package com.streetdom.adapters.in.web.mapper;

import com.streetdom.adapters.in.web.request.LoginUserRequest;
import com.streetdom.adapters.in.web.request.RegisterUserRequest;
import com.streetdom.adapters.in.web.response.AuthResponse;
import com.streetdom.adapters.in.web.response.TokensResponse;
import com.streetdom.adapters.in.web.response.UserResponse;
import com.streetdom.application.command.LoginUserCommand;
import com.streetdom.application.command.RegisterUserCommand;
import com.streetdom.application.result.AuthResult;
import com.streetdom.application.result.TokensResult;
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
