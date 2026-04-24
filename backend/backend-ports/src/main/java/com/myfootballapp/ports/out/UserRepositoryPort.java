package com.myfootballapp.ports.out;


import com.myfootballapp.domain.model.User;

public interface UserRepositoryPort {
    User save(User user);
}
