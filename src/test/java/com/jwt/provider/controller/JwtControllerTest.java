package com.jwt.provider.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.provider.model.AuthEntity;
import com.jwt.provider.repository.JwtRepository;
import com.jwt.provider.service.JwtService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@AutoConfigureMockMvc 
public class JwtControllerTest {
    
    @Autowired
    private JwtController jwtController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtRepository jwtRepository;

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @TestConfiguration
    static class MockClockConfig {
        @Bean
        public Clock clock() { 
            return Clock.fixed(Instant.parse("2020-10-26T00:00:00Z"), ZoneId.of("UTC"));
        } 
    }

    @BeforeEach
    public void init() {
        if(!jwtRepository.existsAuthEntityByUserName("testuser123")) {
            jwtRepository.save(new AuthEntity("testuser123", "apikey408374"));
        }
    }

    @Test
	public void contextLoads() throws Exception {
        assertThat(jwtController).isNotNull();
        assertThat(mockMvc).isNotNull();
        assertThat(jwtRepository).isNotNull();
        assertThat(objectMapper).isNotNull();
        assertThat(jwtService).isNotNull();
    }
    
    @Test
    public void retrieveJwtValidUsernameAndApiKey() throws Exception {
        String expectedJwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjEyMyIsImlhdCI6MTYwMzY3MDQwMCwiaXNzIjoiWVd4cWEzTmtabXBzWVhOa2EyRXdhbVE1WmpCcVlXWXdPV293Wm1Fd09XcG1NRGxxTUdGaCIsImV4cCI6MTYwMzc1NjgwMH0.nZ06Z-VdQ7lryfKF6mDJVK_1mjs0MmpnSvBBLWTdfAQ";
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/jwt/request").contentType("application/json").accept("application/json").content(objectMapper.writeValueAsString(new AuthEntity("testuser123", "apikey408374"))))
            .andExpect(status().isOk()).andReturn();
        String jwtToken = mvcResult.getResponse().getContentAsString();
        assertThat(jwtToken).isEqualTo(expectedJwtToken);
    }

    @Test
    public void retrieveJwtInValidUsername() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/jwt/request").contentType("application/json").accept("application/json").content(objectMapper.writeValueAsString(new AuthEntity("invaliduser123", "apikey408374"))))
        .andExpect(status().isUnauthorized());
    }

    @Test
    public void retrieveJwtInValidApiKey() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/jwt/request").contentType("application/json").accept("application/json").content(objectMapper.writeValueAsString(new AuthEntity("testuser123", "invalidapikey408374"))))
        .andExpect(status().isUnauthorized());
    }

    @Test
    public void retrieveJwtMissingUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/jwt/request").contentType("application/json").accept("application/json").content("{\"apiKey\":\"apikey408374\"}"))
        .andExpect(status().isUnauthorized());
    }

    @Test
    public void retrieveJwtMissingApiKey() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/jwt/request").contentType("application/json").accept("application/json").content("{\"user\":\"testuser123\"}"))
        .andExpect(status().isUnauthorized());
    }

    @Test
    public void validJwtRequest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/jwt/request").contentType("application/json").accept("application/json").content(objectMapper.writeValueAsString(new AuthEntity("testuser123", "apikey408374"))))
            .andExpect(status().isOk()).andReturn();
        String jwtToken = mvcResult.getResponse().getContentAsString();
        String validJwtToken = "{\"jwt\":\"" + jwtToken + "\"}";
        this.mockMvc.perform(MockMvcRequestBuilders.post("/jwt/validate").contentType("application/json").accept("application/json").content(validJwtToken))
            .andExpect(status().isOk());
    }

    @Test
    public void InvalidJwtRequest() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/jwt/request").contentType("application/json").accept("application/json").content(objectMapper.writeValueAsString(new AuthEntity("testuser123", "apikey408374"))))
            .andExpect(status().isOk()).andReturn();
        String jwtToken = mvcResult.getResponse().getContentAsString();
        jwtToken = jwtToken.substring(0, jwtToken.length()-1);
        String validJwtToken = "{\"jwt\":\"" + jwtToken + "\"}";
        this.mockMvc.perform(MockMvcRequestBuilders.post("/jwt/validate").contentType("application/json").accept("application/json").content(validJwtToken))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void emptyApiKey() {
        assertEquals(false, jwtService.isAuthorized(new AuthEntity("username", "")));
    }

    @Test
    public void expiredJwt() throws Exception {
        String expiredJwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlcjEyMyIsImlhdCI6MTYwMzY3MDQwMCwiaXNzIjoiWVd4cWEzTmtabXBzWVhOa2EyRXdhbVE2WmpCcVlXWXdPV293Wm1Fd09XcG1NRGxxTUdGaCIsImV4cCI6MTYwMzc1NjgwMH0.nZ06Z-VdQ7lryfKF6mDJVK_1mjs0MmpnSvBBLWTdfAQ";
        //this.mockMvc.perform(MockMvcRequestBuilders.post("/jwt/request").contentType("application/json").accept("application/json").content(objectMapper.writeValueAsString(new AuthEntity("testuser123", "apikey408374"))))
            //.andExpect(status().isOk());
        assertEquals(false, jwtService.isValidToken(expiredJwtToken));
    }
}