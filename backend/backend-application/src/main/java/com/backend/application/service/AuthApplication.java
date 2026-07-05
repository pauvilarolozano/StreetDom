package com.backend.application.service;

import com.backend.domain.exception.InvalidCredentialsException;
import com.backend.domain.exception.RefreshTokenNotFoundException;
import com.backend.domain.exception.UserAlreadyExistsException;
import com.backend.application.mapper.AuthApplicationMapper;
import com.backend.domain.model.RefreshToken;
import com.backend.ports.in.dto.LoginUserCommand;
import com.backend.ports.in.dto.RegisterUserCommand;
import com.backend.ports.in.dto.AuthResult;
import com.backend.domain.model.User;
import com.backend.ports.in.dto.TokensResult;
import com.backend.ports.in.useCase.AuthUseCase;
import com.backend.ports.out.PasswordServicePort;
import com.backend.ports.out.RefreshTokenRepositoryPort;
import com.backend.ports.out.UserRepositoryPort;
import com.backend.ports.out.TokenServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthApplication implements AuthUseCase {

    private final UserRepositoryPort userRepository;
    private final RefreshTokenRepositoryPort refreshTokenRepository;
    private final TokenServicePort tokenService;
    private final PasswordServicePort passwordService;
    private final AuthApplicationMapper mapper;

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

        String accessToken = tokenService.generateAccessToken(userSaved.getUsername(), userSaved.getEmail());
        String refreshTokenValue = tokenService.generateRefreshToken(userSaved.getUsername());
        refreshTokenRepository.save(RefreshToken.create(refreshTokenValue, userSaved));

        return mapper.toResult(userSaved,accessToken,refreshTokenValue);
    }

    @Override
    public AuthResult login(LoginUserCommand userCommand) {

        User user = userRepository.findByUsername(userCommand.username())
                .orElseThrow(InvalidCredentialsException::new);

       if (!passwordService.matches(userCommand.password(),user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = tokenService.generateAccessToken(user.getUsername(),user.getEmail());
        String refreshTokenValue = tokenService.generateRefreshToken(user.getUsername());
        refreshTokenRepository.save(RefreshToken.create(refreshTokenValue,user));

        return mapper.toResult(user,accessToken,refreshTokenValue);
    }

    @Override
    @Transactional
    //TODO anadir campo familyId para invalidar anteriores refreshtoken del usuario en ese dispositivo
    //TODO: atomic update to avoid future problems with concurrency if 2 refresh request at the same time
    public TokensResult refresh(String oldRefreshTokenValue) {

        RefreshToken oldRefreshToken = refreshTokenRepository.findByToken(oldRefreshTokenValue)
                .orElseThrow(RefreshTokenNotFoundException::new);

        oldRefreshToken.validate();
        oldRefreshToken.revoke();
        refreshTokenRepository.save(oldRefreshToken);

        User userFromToken = oldRefreshToken.getUser();

        String newAccessToken = tokenService.generateAccessToken(userFromToken.getUsername(), userFromToken.getEmail());
        String newRefreshTokenValue = tokenService.generateRefreshToken(userFromToken.getUsername());

        RefreshToken newRefreshToken = RefreshToken.createFromRotation(
                newRefreshTokenValue,
                userFromToken,
                oldRefreshToken.getSessionMaxUntil()
        );
        refreshTokenRepository.save(newRefreshToken);

        return new TokensResult(newAccessToken,newRefreshTokenValue);
    }
}
