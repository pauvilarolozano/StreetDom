package com.streetdom.application.port.out.auth;

import com.streetdom.domain.model.auth.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);

    void save(User user);

    boolean existsByUsername(String username);
}
