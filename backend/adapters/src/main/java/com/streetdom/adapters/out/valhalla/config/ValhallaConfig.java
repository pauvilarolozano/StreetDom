package com.streetdom.adapters.out.valhalla.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.Builder;

@Configuration
public class ValhallaConfig {

    @Bean("valhallaRestClient")
    public RestClient valhallaRestClient(Builder builder, ValhallaProperties properties) {
        return builder
                .baseUrl(properties.url())
                .build();
    }
}
