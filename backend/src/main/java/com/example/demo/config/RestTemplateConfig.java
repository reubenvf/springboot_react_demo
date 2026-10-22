package com.example.demo.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// ReubenVF: A dedicated RestTemplate bean makes it easy to call third-party APIs from the application layer.
@Configuration
public class RestTemplateConfig {

    // ReubenVF: Builds and exposes a ready-to-use RestTemplate instance for outbound HTTP calls.
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
