package com.streetdom.adapters.out.persistence.mapper;

import com.streetdom.adapters.out.persistence.entity.RefreshTokenEntity;
import com.streetdom.adapters.out.persistence.entity.UserEntity;
import com.streetdom.domain.model.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenMapper {

    private final UserMapper userMapper;

    public RefreshToken toDomain(RefreshTokenEntity entity) {
        return RefreshToken.builder()
                .tokenHash(entity.getTokenHash())
                .expiresAt(entity.getExpiresAt())
                .sessionMaxUntil(entity.getSessionMaxUntil())
                .user(userMapper.toDomain(entity.getUser()))
                .revoked(entity.isRevoked())
                .build();
    }

    public RefreshTokenEntity toEntity(RefreshToken refreshToken) {
        return RefreshTokenEntity.builder()
                .tokenHash(refreshToken.getTokenHash())
                .expiresAt(refreshToken.getExpiresAt())
                .sessionMaxUntil(refreshToken.getSessionMaxUntil())
                .user(userMapper.toEntity(refreshToken.getUser()))
                .revoked(refreshToken.isRevoked())
                .build();
    }
}
