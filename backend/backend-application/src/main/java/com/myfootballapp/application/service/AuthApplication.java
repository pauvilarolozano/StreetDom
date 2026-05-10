package com.myfootballapp.application.service;

import com.myfootballapp.ports.in.command.LoginUserCommand;
import com.myfootballapp.ports.in.command.RegisterUserCommand;
import com.myfootballapp.application.mapper.AuthMapper;
import com.myfootballapp.ports.in.result.AuthResult;
import com.myfootballapp.domain.model.User;
import com.myfootballapp.ports.in.useCase.AuthUseCase;
import com.myfootballapp.ports.out.AuthRepositoryPort;
import com.myfootballapp.ports.out.TokenServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthApplication implements AuthUseCase {

    private final AuthRepositoryPort authRepository;
    private final TokenServicePort tokenService;
    //private final PasswordEncoder passwordEncoder;

    public AuthResult register(RegisterUserCommand userCommand) {

        User userToSave = AuthMapper.userToDomain(userCommand);
        User userSaved = authRepository.save(userToSave);

        return AuthMapper.userToResult(userSaved);
    }

    public AuthResult login(LoginUserCommand userCommand) {
        return null;
    }

    public AuthResult refresh(String refreshToken) {
        return null;
    }
}
