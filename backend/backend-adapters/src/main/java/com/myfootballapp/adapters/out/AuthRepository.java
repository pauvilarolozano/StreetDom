package com.myfootballapp.adapters.out;

import com.myfootballapp.adapters.out.Entity.UserEntity;
import com.myfootballapp.adapters.out.mapper.UserEntityMapper;
import com.myfootballapp.domain.model.User;
import com.myfootballapp.ports.out.AuthRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthRepository implements AuthRepositoryPort {

    private final JpaAuthRepository jpa;

    public User save(User user) {
        UserEntity userToSave = UserEntityMapper.toEntity(user);
        UserEntity result = jpa.save(userToSave);
        return UserEntityMapper.toDomain(result);
    }
}
