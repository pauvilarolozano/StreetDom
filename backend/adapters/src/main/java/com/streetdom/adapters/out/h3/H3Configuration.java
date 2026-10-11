package com.streetdom.adapters.out.h3;

import com.uber.h3core.H3Core;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class H3Configuration {

    @Bean
    public H3Core h3Core() throws IOException {
        return H3Core.newInstance();
    }
}
