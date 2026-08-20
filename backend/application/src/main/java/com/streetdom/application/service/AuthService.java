package com.streetdom.application.service;

import com.streetdom.application.port.out.*;
import com.streetdom.application.port.out.result.RefreshTokenBundle;
import com.streetdom.domain.exception.InvalidCredentialsException;
import com.streetdom.domain.exception.RefreshTokenNotFoundException;
import com.streetdom.domain.exception.UserAlreadyExistsException;
import com.streetdom.application.mapper.AuthServiceMapper;
import com.streetdom.domain.model.RefreshToken;
import com.streetdom.application.command.LoginUserCommand;
import com.streetdom.application.command.RegisterUserCommand;
import com.streetdom.application.port.in.result.AuthResult;
import com.streetdom.domain.model.User;
import com.streetdom.application.port.in.result.TokensResult;
import com.streetdom.application.port.in.AuthUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenService tokenService;
    private final RefreshTokenFactory refreshTokenFactory;
    private final TokenHasher tokenHasher;
    private final PasswordHasher passwordHasher;
    private final AuthServiceMapper authMapper;

    @Override
    @Transactional
    public AuthResult register(RegisterUserCommand userCommand) {

        if (userRepository.existsByUsername(userCommand.username())) {
            throw new UserAlreadyExistsException();
        }

        // TODO: check minimum security of the new password

        String passwordHash = passwordHasher.hash(userCommand.password());
        User newUser = authMapper.userToDomain(userCommand, passwordHash);
        userRepository.save(newUser);

        String newAccessToken = tokenService.generateAccessToken(newUser.getUsername(), newUser.getEmail());
        RefreshTokenBundle generatedRefresh = refreshTokenFactory.create(newUser);
        refreshTokenRepository.save(generatedRefresh.domainToken());

        return authMapper.authSessionToResult(newUser, newAccessToken, generatedRefresh.rawTokenValue());
    }

    @Override
    @Transactional
    public AuthResult login(LoginUserCommand userCommand) {

        User user = userRepository.findByUsername(userCommand.username())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordHasher.matches(userCommand.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        refreshTokenRepository.revokeAllByUserId(user.getId());
        String newAccessToken = tokenService.generateAccessToken(user.getUsername(), user.getEmail());
        RefreshTokenBundle generatedRefresh = refreshTokenFactory.create(user);
        refreshTokenRepository.save(generatedRefresh.domainToken());

        return authMapper.authSessionToResult(user, newAccessToken, generatedRefresh.rawTokenValue());
    }

    @Override
    @Transactional
    // TODO: atomic update to avoid future problems with concurrency if 2 refresh
    // request at the same time?
    public TokensResult refresh(String refreshToken) {

        tokenService.validateRefreshToken(refreshToken);
        String refreshTokenHash = tokenHasher.hash(refreshToken);

        RefreshToken storedRefreshToken = refreshTokenRepository.findByTokenHash(refreshTokenHash)
                .orElseThrow(RefreshTokenNotFoundException::new);

        storedRefreshToken.validate(Instant.now());
        storedRefreshToken.revoke();
        refreshTokenRepository.save(storedRefreshToken);

        User user = storedRefreshToken.getUser();

        String newAccessToken = tokenService.generateAccessToken(user.getUsername(), user.getEmail());
        RefreshTokenBundle generatedRefresh = refreshTokenFactory.rotate(storedRefreshToken);
        refreshTokenRepository.save(generatedRefresh.domainToken());

        return new TokensResult(newAccessToken, generatedRefresh.rawTokenValue());
    }

    @Override
    @Transactional
    public void logout(String refreshToken) {

        if (refreshToken == null || refreshToken.isBlank()) {
            return;
        }

        String refreshTokenHash = tokenHasher.hash(refreshToken);

        refreshTokenRepository.findByTokenHash(refreshTokenHash)
                .ifPresent(activeRefreshToken -> {
                    activeRefreshToken.revoke();
                    refreshTokenRepository.save(activeRefreshToken);
                });
    }
}
