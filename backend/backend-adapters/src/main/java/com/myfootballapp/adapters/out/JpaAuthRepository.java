package com.myfootballapp.adapters.out;
import com.myfootballapp.adapters.out.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface JpaAuthRepository extends JpaRepository<UserEntity, Long> {
}
