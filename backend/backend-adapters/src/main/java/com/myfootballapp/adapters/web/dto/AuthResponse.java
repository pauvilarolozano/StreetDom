package com.myfootballapp.adapters.web.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthResponse {
    UserResponse user;
    String accesToken;
    String refreshToken;
}
