package com.streetdom.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Entity
@Table(name="refresh_token")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenEntity {

    @Id
    private String tokenHash;
    private Instant expiresAt;
    private Instant sessionMaxUntil;
    private boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}
