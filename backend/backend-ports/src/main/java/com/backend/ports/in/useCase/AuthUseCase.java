package com.backend.ports.in.useCase;

import com.backend.ports.in.dto.TokensResult;
import com.backend.ports.in.dto.LoginUserCommand;
import com.backend.ports.in.dto.RegisterUserCommand;
import com.backend.ports.in.dto.AuthResult;

public interface AuthUseCase {
    AuthResult register(RegisterUserCommand userCommand);
    AuthResult login(LoginUserCommand userCommand);
    TokensResult refresh(String oldRefreshToken);
}
