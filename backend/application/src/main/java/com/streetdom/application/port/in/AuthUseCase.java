package com.streetdom.application.port.in;

import com.streetdom.application.result.TokensResult;
import com.streetdom.application.command.LoginUserCommand;
import com.streetdom.application.command.RegisterUserCommand;
import com.streetdom.application.result.AuthResult;

public interface AuthUseCase {
    AuthResult register(RegisterUserCommand userCommand);
    AuthResult login(LoginUserCommand userCommand);
    TokensResult refresh(String oldRefreshToken);
}
