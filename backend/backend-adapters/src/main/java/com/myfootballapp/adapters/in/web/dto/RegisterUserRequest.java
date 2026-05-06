package com.myfootballapp.adapters.in.web.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RegisterUserRequest {
    String username;
    String password;
    String firstName;
    String lastName;
    String email;
}
