package com.myfootballapp.ports.in.useCase;


import com.myfootballapp.ports.in.command.LoginUserCommand;
import com.myfootballapp.ports.in.command.RegisterUserCommand;
import com.myfootballapp.ports.in.result.AuthResult;

public interface AuthUseCase {
    AuthResult register(RegisterUserCommand userCommand);
    AuthResult login(LoginUserCommand userCommand);
    AuthResult refresh(String refreshToken);

}
