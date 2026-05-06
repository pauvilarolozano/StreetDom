package com.myfootballapp.ports.in.command;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginUserCommand {
    String username;
    String password;
}
