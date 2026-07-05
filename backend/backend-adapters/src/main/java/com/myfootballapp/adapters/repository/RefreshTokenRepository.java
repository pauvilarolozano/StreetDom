package com.myfootballapp.adapters.repository;

import com.myfootballapp.adapters.repository.entity.RefreshTokenEntity;
import com.myfootballapp.adapters.repository.mapper.RefreshTokenMapper;
import com.myfootballapp.domain.model.RefreshToken;
import com.myfootballapp.ports.out.RefreshTokenRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository implements RefreshTokenRepositoryPort {

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
