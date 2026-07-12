package com.streetdom.application.service;

import com.streetdom.application.port.out.*;
import com.streetdom.application.port.out.*;
import com.streetdom.application.port.out.*;
import com.streetdom.domain.exception.InvalidCredentialsException;
import com.streetdom.domain.exception.RefreshTokenNotFoundException;
import com.streetdom.domain.exception.UserAlreadyExistsException;
import com.streetdom.application.mapper.AuthServiceMapper;
import com.streetdom.domain.model.RefreshToken;
import com.streetdom.application.command.LoginUserCommand;
import com.streetdom.application.command.RegisterUserCommand;
import com.streetdom.application.result.AuthResult;
import com.streetdom.domain.model.User;
import com.streetdom.application.result.TokensResult;
import com.streetdom.application.port.in.AuthUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenService tokenService;
    private final RefreshTokenFactory refreshTokenFactory;
    private final PasswordService passwordService;
    private final AuthServiceMapper mapper;

    @Override
    @Transactional
    public AuthResult register(RegisterUserCommand userCommand) {

        if (userRepository.existsByUsername(userCommand.username())) {
            throw new UserAlreadyExistsException();
        }

        //TODO: check minimum security of the new password
        //passwordService.isValid(userCommand.getPassword())

        String passwordHash = passwordService.hash(userCommand.password());
        User userToSave = mapper.toDomain(userCommand, passwordHash);
        User userSaved = userRepository.save(userToSave);

        String newAccessToken = tokenService.generateAccessToken(userSaved.getUsername(), userSaved.getEmail());
        RefreshToken newRefreshToken = refreshTokenFactory.create(userSaved);
        refreshTokenRepository.save(newRefreshToken);

        return mapper.toResult(userSaved,newAccessToken,newRefreshToken.getValue());
    }

    @Override
    public AuthResult login(LoginUserCommand userCommand) {

        User user = userRepository.findByUsername(userCommand.username())
                .orElseThrow(InvalidCredentialsException::new);

       if (!passwordService.matches(userCommand.password(),user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        String newAccessToken = tokenService.generateAccessToken(user.getUsername(),user.getEmail());
        RefreshToken newRefreshToken = refreshTokenFactory.create(user);
        refreshTokenRepository.save(newRefreshToken);

        return mapper.toResult(user,newAccessToken,newRefreshToken.getValue());
    }

    @Override
    @Transactional
    //TODO anadir campo familyId para invalidar anteriores refreshtoken del usuario en ese dispositivo
    //TODO: atomic update to avoid future problems with concurrency if 2 refresh request at the same time
    public TokensResult refresh(String oldRefreshTokenValue) {

        RefreshToken oldRefreshToken = refreshTokenRepository.findByToken(oldRefreshTokenValue)
                .orElseThrow(RefreshTokenNotFoundException::new);

        oldRefreshToken.validate(Instant.now());
        oldRefreshToken.revoke();
        refreshTokenRepository.save(oldRefreshToken);

        User userFromToken = oldRefreshToken.getUser();

        String newAccessToken = tokenService.generateAccessToken(userFromToken.getUsername(), userFromToken.getEmail());
        RefreshToken newRefreshToken = refreshTokenFactory.rotate(oldRefreshToken);
        refreshTokenRepository.save(newRefreshToken);

        return new TokensResult(newAccessToken,newRefreshToken.getValue());
    }
}
