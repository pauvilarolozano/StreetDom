package com.myfootballapp.ports.in.result;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class AuthResult {
    Long id;
    String username;
    String firstName;
    String lastName;
    String email;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
