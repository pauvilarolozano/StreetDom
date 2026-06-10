package com.myfootballapp.ports.in.dto;

import com.myfootballapp.domain.model.User;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthResult {
    User user;
    String accessToken;
    String refreshToken;
}
