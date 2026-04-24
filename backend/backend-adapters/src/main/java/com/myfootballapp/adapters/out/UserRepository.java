package com.myfootballapp.adapters.out;

import com.myfootballapp.adapters.out.mapper.UserEntityMapper;
import com.myfootballapp.domain.model.User;
import com.myfootballapp.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserRepositoryPort {

    private final JpaUserRepository jpa;

    public User save(User user) {
        UserEntity userToSave = UserEntityMapper.toEntity(user);
        UserEntity result = jpa.save(userToSave);
        return UserEntityMapper.toDomain(result);
    }
}
