package com.jwt.provider.config;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {
    @Bean
    public Clock clock() { 
        return Clock.systemDefaultZone();
    } 
}
