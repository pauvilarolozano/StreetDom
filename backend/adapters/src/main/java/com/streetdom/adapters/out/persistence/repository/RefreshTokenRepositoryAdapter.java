package com.streetdom.adapters.out.persistence.repository;

import com.streetdom.adapters.out.persistence.entity.RefreshTokenEntity;
import com.streetdom.adapters.out.persistence.jpa.RefreshTokenJpaRepository;
import com.streetdom.adapters.out.persistence.mapper.RefreshTokenMapper;
import com.streetdom.application.port.out.RefreshTokenRepository;
import com.streetdom.domain.model.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;
    private final RefreshTokenMapper refreshMapper;

    @Override
    public void save(RefreshToken refreshToken) {
        RefreshTokenEntity refreshTokenSaved =
                refreshTokenJpaRepository.save(refreshMapper.toEntity(refreshToken));

        refreshMapper.toDomain(refreshTokenSaved);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenJpaRepository.findByToken(token).map(refreshMapper::toDomain);
    }
}
