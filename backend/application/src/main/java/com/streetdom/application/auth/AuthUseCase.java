package com.streetdom.application.auth;

import com.streetdom.application.auth.result.TokensResult;
import com.streetdom.application.auth.command.LoginUserCommand;
import com.streetdom.application.auth.command.RegisterUserCommand;
import com.streetdom.application.auth.result.SessionResult;
import com.streetdom.application.auth.result.UserResult;

public interface AuthUseCase {

    SessionResult register(RegisterUserCommand userCommand);

    SessionResult login(LoginUserCommand userCommand);

    TokensResult refresh(String refreshToken);

    void logout(String refreshToken);

    UserResult me(String username);

}
