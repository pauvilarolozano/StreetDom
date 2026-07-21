package com.streetdom.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {
    Long id;
    String username;
    String email;
    String passwordHash;
    @Builder.Default
    Role role = Role.USER;

}
