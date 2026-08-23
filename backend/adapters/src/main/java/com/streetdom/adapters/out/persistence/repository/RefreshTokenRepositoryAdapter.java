package com.streetdom.adapters.out.persistence.repository;

import com.streetdom.adapters.out.persistence.entity.UserEntity;
import com.streetdom.adapters.out.persistence.jpa.RefreshTokenJpaRepository;
import com.streetdom.adapters.out.persistence.jpa.UserJpaRepository;
import com.streetdom.adapters.out.persistence.mapper.RefreshTokenMapper;
import com.streetdom.application.port.out.RefreshTokenRepository;
import com.streetdom.domain.model.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;
    private final RefreshTokenMapper refreshMapper;
    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<RefreshToken> findByTokenHash(String token) {
        return refreshTokenJpaRepository.findByTokenHash(token).map(refreshMapper::toDomain);
    }

    @Override
    public void save(RefreshToken refreshToken) {
        refreshTokenJpaRepository.save(refreshMapper.toEntity(refreshToken));
    }

    @Override
    public void revokeAllByUserId(UUID userId) {
        refreshTokenJpaRepository.revokeAllByUserId(userId);
    }
}
