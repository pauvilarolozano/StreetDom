package com.myfootballapp.application.mapper;

import com.myfootballapp.domain.model.User;
import com.myfootballapp.ports.in.dto.LoginUserCommand;
import com.myfootballapp.ports.in.dto.RegisterUserCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthApplicationMapper {

    @Mapping(target = "password", source = "passwordEncoded")
    User toDomain(RegisterUserCommand userCommand, String passwordEncoded);

}
