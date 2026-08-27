package com.streetdom.adapters.out.persistence;

import com.streetdom.adapters.out.persistence.jpa.UserJpaRepository;
import com.streetdom.adapters.out.persistence.mapper.UserMapper;
import com.streetdom.application.port.out.UserRepository;
import com.streetdom.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(userMapper::toDomain);
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(userMapper.toEntity(user));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }
}
