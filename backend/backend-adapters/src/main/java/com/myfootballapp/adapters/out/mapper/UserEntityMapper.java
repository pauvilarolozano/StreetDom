package com.myfootballapp.adapters.out.mapper;

import com.myfootballapp.adapters.out.UserEntity;
import com.myfootballapp.domain.model.User;

public class UserEntityMapper {
    public static User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }

    public static UserEntity toEntity(User user) {
        return new UserEntity(user.getId(), user.getEmail(), user.getPassword());
    }
}
