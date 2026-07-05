package com.backend.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {
    Long id;
    String username;
    String passwordHash;
    String firstName;
    String lastName;
    String email;
    @Builder.Default
    Role role = Role.USER;

}
