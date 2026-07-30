package com.streetdom.adapters.out.jwt;

import com.streetdom.application.port.out.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenService implements TokenService {

    //TODO: anadir variables de entorno a docker
    private final JwtConfigProperties jwtProperties;
    private final Key secretKey;

    public JwtTokenService(JwtConfigProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    @Override
    public String generateAccessToken(String username, String email) {
        return Jwts.builder()
                .subject(username)
                .claim("email", email)
                .claim("type", "access")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessExpiration()))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .subject(username)
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpiration()))
                .signWith(secretKey)
                .compact();    }

    @Override
    public boolean isValidAccessToken(String token) {

        try {
            return !isExpired(token) && isAccessToken(token);

        } catch (JwtException | IllegalArgumentException e) {
            return false;

        }
    }

    @Override
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public Instant extractExpiration(String token) {
        return getClaims(token).getExpiration().toInstant();
    }

    private boolean isAccessToken(String token) {
        return "access".equals(getClaims(token).get("type",String.class));
    }

    private boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());

    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
