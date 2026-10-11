package com.streetdom.application.auth;

import com.streetdom.application.auth.result.UserResult;
import com.streetdom.application.port.out.auth.*;
import com.streetdom.application.port.out.auth.RefreshTokenBundle;
import com.streetdom.domain.exception.auth.InvalidCredentialsException;
import com.streetdom.domain.exception.auth.RefreshTokenNotFoundException;
import com.streetdom.domain.exception.auth.UserAlreadyExistsException;
import com.streetdom.domain.exception.auth.UserNotFoundException;
import com.streetdom.domain.model.auth.RefreshToken;
import com.streetdom.application.auth.command.LoginUserCommand;
import com.streetdom.application.auth.command.RegisterUserCommand;
import com.streetdom.application.auth.result.SessionResult;
import com.streetdom.domain.model.auth.User;
import com.streetdom.application.auth.result.TokensResult;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenSigner tokenSigner;
    private final RefreshTokenFactory refreshTokenFactory;
    private final TokenHasher tokenHasher;
    private final PasswordHasher passwordHasher;
    private final AuthServiceMapper authMapper;
    private final Clock clock;

    @Override
    @Transactional
    public SessionResult register(RegisterUserCommand command) {

        String username = command.username();

        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(username);
        }

        // TODO: check minimum security of the new password

        String passwordHash = passwordHasher.hash(command.password());
        User newUser = User.create(username,command.email(),passwordHash);
        userRepository.save(newUser);

        String newAccessToken = tokenSigner.generateAccessToken(newUser.getUsername(), newUser.getEmail());
        RefreshTokenBundle generatedRefresh = refreshTokenFactory.create(newUser);
        refreshTokenRepository.save(generatedRefresh.domainToken());

        return authMapper.toResult(newUser, newAccessToken, generatedRefresh.rawTokenValue());
    }

    @Override
    @Transactional
    public SessionResult login(LoginUserCommand command) {

        User user = userRepository.findByUsername(command.username())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordHasher.matches(command.password(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        refreshTokenRepository.revokeAllByUserId(user.getId());
        String newAccessToken = tokenSigner.generateAccessToken(user.getUsername(), user.getEmail());
        RefreshTokenBundle generatedRefresh = refreshTokenFactory.create(user);
        refreshTokenRepository.save(generatedRefresh.domainToken());

        return authMapper.toResult(user, newAccessToken, generatedRefresh.rawTokenValue());
    }

    @Override
    @Transactional
    // TODO: atomic update to avoid future problems with concurrency if 2 refresh
    // request at the same time?
    public TokensResult refresh(String refreshToken) {

        tokenSigner.validateRefreshToken(refreshToken);
        String refreshTokenHash = tokenHasher.hash(refreshToken);
        RefreshToken storedRefreshToken = refreshTokenRepository.findByTokenHash(refreshTokenHash)
                .orElseThrow(RefreshTokenNotFoundException::new);

        storedRefreshToken.validate(clock.instant());
        storedRefreshToken.revoke();
        refreshTokenRepository.save(storedRefreshToken);

        User user = storedRefreshToken.getUser();

        String newAccessToken = tokenSigner.generateAccessToken(user.getUsername(), user.getEmail());
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

        refreshTokenRepository.findByTokenHash(tokenHasher.hash(refreshToken))
                .ifPresent(activeRefreshToken -> {
                    activeRefreshToken.revoke();
                    refreshTokenRepository.save(activeRefreshToken);
                });
    }

    @Override
    public UserResult me(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return authMapper.toResult(user);
    }
}
