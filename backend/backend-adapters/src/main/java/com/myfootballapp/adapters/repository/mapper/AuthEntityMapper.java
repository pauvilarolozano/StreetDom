package com.myfootballapp.adapters.repository.mapper;

import com.myfootballapp.adapters.repository.Entity.UserEntity;
import com.myfootballapp.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthEntityMapper {
    User toDomain(UserEntity entity);
    UserEntity toEntity(User user);
}
