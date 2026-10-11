package com.streetdom.application.port.out.auth;

public interface TokenHasher {
    String hash(String token);
}
