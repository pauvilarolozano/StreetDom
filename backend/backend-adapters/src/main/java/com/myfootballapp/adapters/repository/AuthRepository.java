package com.myfootballapp.adapters.repository;

import com.myfootballapp.adapters.repository.Entity.UserEntity;
import com.myfootballapp.adapters.repository.mapper.AuthEntityMapper;
import com.myfootballapp.domain.model.User;
import com.myfootballapp.ports.out.AuthRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthRepository implements AuthRepositoryPort {

    private final JpaAuthRepository jpa;
    private final AuthEntityMapper mapper;

    public User save(User user) {
        UserEntity userToSave = mapper.toEntity(user);
        UserEntity result = jpa.save(userToSave);
        return mapper.toDomain(result);
    }

    public Optional<User> findByUsername(String username) {
        return jpa.findByUsername(username).map(mapper::toDomain);
    }

    public boolean existUsername(String username) {
        return jpa.existByUsername(username);
    }
}
