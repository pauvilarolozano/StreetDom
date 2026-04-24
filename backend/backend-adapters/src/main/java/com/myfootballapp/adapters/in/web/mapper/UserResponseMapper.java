package com.myfootballapp.adapters.in.web.mapper;

import com.myfootballapp.adapters.in.web.dto.UserRequest;
import com.myfootballapp.adapters.in.web.dto.UserResponse;
import com.myfootballapp.domain.model.User;

public class UserResponseMapper {

    public static UserResponse toResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

}
