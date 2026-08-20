package com.streetdom.adapters.out.jwt;

import com.streetdom.application.port.out.TokenService;
import com.streetdom.domain.exception.InvalidTokenException;
import com.streetdom.domain.exception.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenService implements TokenService {

    private final JwtConfigProperties jwtProperties;
    private final Key secretKey;

    public JwtTokenService(JwtConfigProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateAccessToken(String username, String email) {
        Date now = new Date();
        return Jwts.builder()
                .subject(username)
                .claim("email", email)
                .claim("type", "access")
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtProperties.getAccessExpiration()))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String generateRefreshToken(String username) {
        Date now = new Date();
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .subject(username)
                .claim("type", "refresh")
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtProperties.getRefreshExpiration()))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public void validateAccessToken(String token) {
        validateTokenType(token, "access");
    }

    @Override
    public void validateRefreshToken(String token) {
        validateTokenType(token, "refresh");
    }

    @Override
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public Instant extractExpiration(String token) {
        return getClaims(token).getExpiration().toInstant();
    }

    private void validateTokenType(String token, String expectedType) {
        try {
            Claims claims = getClaims(token);
            if (!expectedType.equals(claims.get("type", String.class))) {
                throw new InvalidTokenException("Provided token is not of type: " + expectedType);
            }

        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();

        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("Invalid structure or signature for " + expectedType + " token");
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
