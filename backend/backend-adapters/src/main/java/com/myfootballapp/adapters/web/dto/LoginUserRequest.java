package com.myfootballapp.adapters.web.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginUserRequest {
    String username;
    String password;
}
