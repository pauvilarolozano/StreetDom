package com.myfootballapp.ports.in;

import com.myfootballapp.domain.model.User;

public interface UserUseCase {
    User register(User user);
}
