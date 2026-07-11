package com.backend.adapters.out.persistence.repository;

import com.backend.adapters.out.persistence.entity.RefreshTokenEntity;
import com.backend.adapters.out.persistence.jpa.RefreshTokenJpaRepository;
import com.backend.adapters.out.persistence.mapper.RefreshTokenMapper;
import com.backend.application.port.out.RefreshTokenRepositoryPort;
import com.backend.domain.model.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepositoryPort {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;
    private final RefreshTokenMapper refreshMapper;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        RefreshTokenEntity refreshTokenSaved =
                refreshTokenJpaRepository.save(refreshMapper.toEntity(refreshToken));

        return refreshMapper.toDomain(refreshTokenSaved);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenJpaRepository.findByToken(token).map(refreshMapper::toDomain);
    }
}
