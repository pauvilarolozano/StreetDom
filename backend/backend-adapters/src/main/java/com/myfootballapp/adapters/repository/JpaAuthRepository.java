package com.myfootballapp.adapters.repository;
import com.myfootballapp.adapters.repository.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface JpaAuthRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    boolean existByUsername(String username);
}
