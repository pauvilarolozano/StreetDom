package com.streetdom.adapters.out.persistence;

import com.streetdom.adapters.out.persistence.jpa.RefreshTokenJpaRepository;
import com.streetdom.adapters.out.persistence.mapper.RefreshTokenMapper;
import com.streetdom.application.port.out.RefreshTokenRepository;
import com.streetdom.domain.model.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RefreshTokenPersistenceAdapter implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;
    private final RefreshTokenMapper refreshMapper;

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
