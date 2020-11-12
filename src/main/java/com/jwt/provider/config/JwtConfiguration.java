package com.jwt.provider.config;

import java.time.Clock;

import com.jwt.provider.model.StatusEntity;
import com.jwt.provider.model.TokenEntity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {
    @Bean
    public Clock clock() { 
        return Clock.systemDefaultZone();
    } 

    @Bean
    public TokenEntity tokenEntity() {
        return new TokenEntity();
    }

    @Bean
    public StatusEntity statusEntity() {
        return new StatusEntity();
    }
}
