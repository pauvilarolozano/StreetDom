package com.streetdom.adapters.out.crypto;

import com.streetdom.application.port.out.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BCryptPasswordService implements PasswordService {

    private final PasswordEncoder encoder;

    @Override
    public String hash(String password) {
       return encoder.encode(password);
    }

    @Override
    public Boolean matches(String rawPassword, String hashPassword) {
        return encoder.matches(rawPassword,hashPassword);
    }

    @Override
    public Boolean isValid(String password) {
        //TODO: check minimum security of the new password - change method name
        return false;
    }
}
