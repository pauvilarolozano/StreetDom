package com.streetdom.domain.model;

import lombok.Builder;
import lombok.Value;
import java.util.UUID;

@Value
@Builder
public class User {
    UUID id;
    String username;
    String email;
    String passwordHash;
    @Builder.Default
    Role role = Role.USER;

    public static User create(String username, String email, String passwordHash) {
        return User.builder()
                .id(UUID.randomUUID())
                .username(username)
                .email(email)
                .passwordHash(passwordHash)
                .build();
    }
}
