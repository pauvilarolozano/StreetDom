package com.backend.adapters.repository;

import com.backend.adapters.repository.entity.UserEntity;
import com.backend.adapters.repository.mapper.UserMapper;
import com.backend.domain.model.User;
import com.backend.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserRepositoryPort {

    private final UserJpaRepository jpa;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserEntity userSaved = jpa.save(userMapper.toEntity(user));
        return userMapper.toDomain(userSaved);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpa.findByUsername(username).map(userMapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpa.existsByUsername(username);
    }
}
