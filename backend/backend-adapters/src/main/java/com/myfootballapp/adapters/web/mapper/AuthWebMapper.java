package com.myfootballapp.adapters.web.mapper;

import com.myfootballapp.adapters.web.dto.LoginUserRequest;
import com.myfootballapp.adapters.web.dto.RegisterUserRequest;
import com.myfootballapp.adapters.web.dto.UserResponse;
import com.myfootballapp.domain.model.User;
import com.myfootballapp.ports.in.dto.LoginUserCommand;
import com.myfootballapp.ports.in.dto.RegisterUserCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthWebMapper {

    LoginUserCommand toCommand(LoginUserRequest request);
    RegisterUserCommand toCommand(RegisterUserRequest request);
    UserResponse toResponse(User user);
}