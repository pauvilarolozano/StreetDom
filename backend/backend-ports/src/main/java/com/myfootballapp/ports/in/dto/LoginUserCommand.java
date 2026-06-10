package com.myfootballapp.ports.in.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginUserCommand {
    String username;
    String password;
}
