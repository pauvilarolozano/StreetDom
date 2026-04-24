package com.myfootballapp.adapters.out;
import org.springframework.data.jpa.repository.JpaRepository;



public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
}
