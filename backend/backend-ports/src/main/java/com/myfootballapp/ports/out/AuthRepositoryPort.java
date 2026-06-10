package com.myfootballapp.ports.out;


import com.myfootballapp.domain.model.User;
import java.util.Optional;


public interface AuthRepositoryPort {
    User save(User user);
    Optional<User> findByUsername(String username);
    boolean existUsername(String username);

}
