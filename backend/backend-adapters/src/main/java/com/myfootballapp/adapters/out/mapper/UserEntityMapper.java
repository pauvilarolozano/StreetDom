package com.myfootballapp.adapters.out.mapper;

import com.myfootballapp.adapters.out.UserEntity;
import com.myfootballapp.domain.model.User;

public class UserEntityMapper {
    public static User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.id)
                .email(entity.email)
                .password(entity.password)
                .build();
    }

    public static UserEntity toEntity(User user) {
        return new UserEntity(user.getId(), user.getEmail());
    }
}
