package com.myfootballapp.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {
    Long id;
    String password;
    String email;

}
