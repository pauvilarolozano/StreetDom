package com.backend.adapters.out.jwt;

import com.backend.application.port.out.TokenServicePort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenService implements TokenServicePort {

    //TODO: secret key debe ser variable de entorno y cambiarla despues
    private final Key secretKey =
            Keys.hmacShaKeyFor(
                    "secret-key-secret-key-secret-key-123".getBytes()
            );

    @Override
    public String generateAccessToken(String username, String email) {
        return Jwts.builder()
                .subject(username)
                .claim("email", email)
                .claim("type", "access")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .subject(username)
                .claim("type", "refresh")
                .issuedAt(new Date())
                .signWith(secretKey)
                .compact();    }

    @Override
    public String extractUsername(String token) {
        return getClaims(token).getSubject();    }

    @Override
    public boolean isValidAccessToken(String token) {

        Claims claims = getClaims(token);

        return claims.getExpiration().after(new Date()) &&
                "access".equals(claims.get("type",String.class));
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
