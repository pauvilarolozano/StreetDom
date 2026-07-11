package com.backend.application.port.out;

public interface PasswordServicePort {
    String hash(String password);
    Boolean matches(String rawPassword, String hash);
    Boolean isValid(String password);

}
