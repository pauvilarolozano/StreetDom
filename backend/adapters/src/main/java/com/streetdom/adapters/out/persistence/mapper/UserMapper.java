package com.streetdom.adapters.out.persistence.mapper;

import com.streetdom.adapters.out.persistence.entity.UserEntity;
import com.streetdom.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .passwordHash(entity.getPasswordHash())
                .userRole(entity.getUserRole())
                .build();
    }

    public UserEntity toEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .userRole(user.getUserRole())
                .build();
    }
}
