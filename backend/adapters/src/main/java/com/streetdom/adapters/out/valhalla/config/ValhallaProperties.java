package com.streetdom.adapters.out.valhalla.config;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "valhalla")
public record ValhallaProperties(
        String url
) {}
