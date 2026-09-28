
package com.streetdom.domain.model.identity;

import lombok.Builder;
import lombok.Getter;
import java.util.Objects;
import java.util.UUID;

@Getter
public class User {

    private final UUID id;
    private final String username;
    private final String email;
    private final String passwordHash;
    private final UserRole userRole;

    @Builder
    private User(
            UUID id,
            String username,
            String email,
            String passwordHash,
            UserRole userRole
    ) {
        this.id = Objects.requireNonNull(id);
        this.username = Objects.requireNonNull(username);
        this.email = Objects.requireNonNull(email);
        this.passwordHash = Objects.requireNonNull(passwordHash);
        this.userRole = Objects.requireNonNull(userRole);

        if (username.isBlank() || email.isBlank() || passwordHash.isBlank()) {
            throw new IllegalArgumentException("User fields cannot be blank");
        }
    }

    public static User create(
            String username,
            String email,
            String passwordHash
    ) {
        return User.builder()
                .id(UUID.randomUUID())
                .username(username)
                .email(email)
                .passwordHash(passwordHash)
                .userRole(UserRole.USER)
                .build();
    }
}
