package com.myfootballapp.adapters.web.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class UserResponse {
    Long id;
    String username;
    String firstName;
    String lastName;
    String email;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
