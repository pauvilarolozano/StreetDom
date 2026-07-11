package com.backend.adapters.out.persistence.repository;

import com.backend.adapters.out.persistence.entity.UserEntity;
import com.backend.adapters.out.persistence.jpa.UserJpaRepository;
import com.backend.adapters.out.persistence.mapper.UserMapper;
import com.backend.application.port.out.UserRepositoryPort;
import com.backend.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

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
