package com.streetdom.adapters.out.persistence.jpa;

import com.streetdom.adapters.out.persistence.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, String> {
    Optional<RefreshTokenEntity> findByToken(String token);

    @Modifying
    @Transactional
    @Query("""
        UPDATE RefreshTokenEntity r
        SET r.revoked = true
        WHERE r.user.id = :userId
    """)
    void revokeAllByUserId(Long userId);
}
