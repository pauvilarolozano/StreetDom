package com.streetdom.application.port.out;

public interface PasswordHasher {
    String hash(String password);

    boolean matches(String rawPassword, String hash);
}
