package com.myfootballapp.ports.in.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RegisterUserCommand {
    String username;
    String password;
    String firstName;
    String lastName;
    String email;
}
