package com.streetdom.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.streetdom")
@ConfigurationPropertiesScan(basePackages = "com.streetdom")
@EntityScan("com.streetdom.adapters.out.persistence.entity")
@EnableJpaRepositories("com.streetdom.adapters.out.persistence.jpa")
public class BackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
