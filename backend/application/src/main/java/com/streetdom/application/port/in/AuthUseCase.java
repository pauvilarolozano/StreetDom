package com.streetdom.application.port.in;

import com.streetdom.application.port.in.result.TokensResult;
import com.streetdom.application.command.LoginUserCommand;
import com.streetdom.application.command.RegisterUserCommand;
import com.streetdom.application.port.in.result.SessionResult;
import com.streetdom.application.port.in.result.UserResult;

public interface AuthUseCase {

    SessionResult register(RegisterUserCommand userCommand);

    SessionResult login(LoginUserCommand userCommand);

    TokensResult refresh(String refreshToken);

    void logout(String refreshToken);

    UserResult me(String username);

}
