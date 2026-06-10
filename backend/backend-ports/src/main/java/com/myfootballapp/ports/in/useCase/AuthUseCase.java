package com.myfootballapp.ports.in.useCase;


import com.myfootballapp.ports.in.dto.LoginUserCommand;
import com.myfootballapp.ports.in.dto.RegisterUserCommand;
import com.myfootballapp.ports.in.dto.AuthResult;

public interface AuthUseCase {
    AuthResult register(RegisterUserCommand userCommand);
    AuthResult login(LoginUserCommand userCommand);
    AuthResult refresh(String refreshToken);

}
