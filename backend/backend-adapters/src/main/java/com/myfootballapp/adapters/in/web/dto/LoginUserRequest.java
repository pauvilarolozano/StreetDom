package com.myfootballapp.adapters.in.web.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginUserRequest {
    String username;
    String password;
}
