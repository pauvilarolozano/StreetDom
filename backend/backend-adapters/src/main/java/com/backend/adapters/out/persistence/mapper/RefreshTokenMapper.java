package com.backend.adapters.out.persistence.mapper;

import com.backend.adapters.out.persistence.entity.RefreshTokenEntity;
import com.backend.domain.model.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenMapper {

    private final UserMapper userMapper;

    public RefreshToken toDomain(RefreshTokenEntity entity) {
        return RefreshToken.builder()
                .value(entity.getToken())
                .user(userMapper.toDomain(entity.getUser()))
                .expiresAt(entity.getExpiresAt())
                .sessionMaxUntil(entity.getSessionMaxUntil())
                .revoked(entity.isRevoked())
                .build();
    }

    public RefreshTokenEntity toEntity(RefreshToken refreshToken) {
        return RefreshTokenEntity.builder()
                .token(refreshToken.getValue())
                .user(userMapper.toEntity(refreshToken.getUser()))
                .expiresAt(refreshToken.getExpiresAt())
                .sessionMaxUntil(refreshToken.getSessionMaxUntil())
                .revoked(refreshToken.isRevoked())
                .build();
    }
}
