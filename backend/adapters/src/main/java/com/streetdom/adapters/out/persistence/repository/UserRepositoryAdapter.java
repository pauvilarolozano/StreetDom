package com.streetdom.adapters.out.persistence.repository;

import com.streetdom.adapters.out.persistence.entity.UserEntity;
import com.streetdom.adapters.out.persistence.jpa.UserJpaRepository;
import com.streetdom.adapters.out.persistence.mapper.UserMapper;
import com.streetdom.application.port.out.UserRepository;
import com.streetdom.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpa;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByUsername(String username) {
        return jpa.findByUsername(username).map(userMapper::toDomain);
    }

    @Override
    public void save(User user) {
        jpa.save(userMapper.toEntity(user));
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpa.existsByUsername(username);
    }
}
