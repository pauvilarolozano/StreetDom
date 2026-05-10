package com.myfootballapp.adapters.repository;
import com.myfootballapp.adapters.repository.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface JpaAuthRepository extends JpaRepository<UserEntity, Long> {
}
