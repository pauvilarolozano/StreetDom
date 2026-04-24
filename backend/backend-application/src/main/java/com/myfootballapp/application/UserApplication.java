package com.myfootballapp.application;

import com.myfootballapp.domain.model.User;
import com.myfootballapp.ports.in.UserUseCase;
import com.myfootballapp.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserApplication implements UserUseCase {

    private final UserRepositoryPort userRepository;

    public User register(User user) {
        return userRepository.save(user);
    }
}
