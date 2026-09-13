package com.streetdom.adapters.out.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtConfigProperties {
    private String secret;
    private long accessExpiration;
    private long refreshExpiration;
    private long sessionMaxExpiration;
}
