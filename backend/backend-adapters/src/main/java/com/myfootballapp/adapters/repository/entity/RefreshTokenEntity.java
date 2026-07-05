package com.myfootballapp.adapters.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name="refresh_token")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenEntity {

    @Id
    private String token;

    private Instant expiresAt;
    private Instant sessionMaxUntil;
    private boolean revoked;

    @ManyToOne
    private UserEntity user;

}
