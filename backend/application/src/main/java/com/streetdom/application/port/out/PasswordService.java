package com.streetdom.application.port.out;

public interface PasswordService {
    String hash(String password);
    Boolean matches(String rawPassword, String hash);
    Boolean isValid(String password);

}
