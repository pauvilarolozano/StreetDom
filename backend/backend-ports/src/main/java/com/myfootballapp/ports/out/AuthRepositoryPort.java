package com.myfootballapp.ports.out;


import com.myfootballapp.domain.model.User;


public interface AuthRepositoryPort {
    User save(User user);

}
