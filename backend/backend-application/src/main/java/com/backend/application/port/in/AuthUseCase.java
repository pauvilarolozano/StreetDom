package com.backend.application.port.in;

import com.backend.application.result.TokensResult;
import com.backend.application.command.LoginUserCommand;
import com.backend.application.command.RegisterUserCommand;
import com.backend.application.result.AuthResult;

public interface AuthUseCase {
    AuthResult register(RegisterUserCommand userCommand);
    AuthResult login(LoginUserCommand userCommand);
    TokensResult refresh(String oldRefreshToken);
}
