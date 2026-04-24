package com.myfootballapp.adapters.in.web.mapper;

import com.myfootballapp.adapters.in.web.dto.UserRequest;
import com.myfootballapp.domain.model.User;

public class UserRequestMapper {

    public static User toDomain(UserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}
