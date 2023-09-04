package com.team6.finalproject.oAuth.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class oAuthConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
