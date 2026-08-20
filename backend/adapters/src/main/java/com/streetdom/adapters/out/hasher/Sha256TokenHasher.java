package com.streetdom.adapters.out.hasher;

import com.streetdom.application.port.out.TokenHasher;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Component
public class Sha256TokenHasher implements TokenHasher {

    public String hash(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("The value to be hashed can not be null or empty");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available",e);
        }
    }
}
