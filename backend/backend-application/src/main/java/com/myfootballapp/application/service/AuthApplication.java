package com.myfootballapp.application.service;

import com.myfootballapp.application.mapper.AuthApplicationMapper;
import com.myfootballapp.ports.in.dto.LoginUserCommand;
import com.myfootballapp.ports.in.dto.RegisterUserCommand;
import com.myfootballapp.ports.in.dto.AuthResult;
import com.myfootballapp.domain.model.User;
import com.myfootballapp.ports.in.useCase.AuthUseCase;
import com.myfootballapp.ports.out.AuthRepositoryPort;
import com.myfootballapp.ports.out.TokenServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthApplication implements AuthUseCase {

    private final AuthRepositoryPort authRepository;
    private final TokenServicePort tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthApplicationMapper mapper;

    public AuthResult register(RegisterUserCommand userCommand) {

        String encodedPassword = passwordEncoder.encode(userCommand.getPassword());
        User userToSave = mapper.toDomain(userCommand, encodedPassword);
        User userSaved = authRepository.save(userToSave);

        return AuthResult.builder()
                .user(userSaved)
                .accessToken(tokenService.generateAccesToken(userSaved))
                .refreshToken( tokenService.generateRefreshToken(userSaved))
                .build();
    }

    public AuthResult login(LoginUserCommand userCommand) {

        User user = authRepository.findByUsername(userCommand.getUsername())
                .orElseThrow(RuntimeException::new);

        boolean validPassword = passwordEncoder.matches(userCommand.getPassword(),user.getPassword());

        if (!validPassword) {
            throw new RuntimeException();
        }

        return AuthResult.builder()
                .user(user)
                .accessToken(tokenService.generateAccesToken(user))
                .refreshToken(tokenService.generateRefreshToken(user))
                .build();
    }

    public AuthResult refresh(String refreshToken) {
        return null;
    }
}
