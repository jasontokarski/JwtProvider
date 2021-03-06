package com.jwt.provider.service;

import com.jwt.provider.model.AuthEntity;

import io.jsonwebtoken.Claims;

import java.time.Clock;
import java.util.Optional;

public interface JwtService {
    Optional<AuthEntity> findByUser(String user);
    public boolean isAuthorized(AuthEntity authEntity);
    public boolean isValidToken(String jwt);
    public String createToken(String subject);
    public Claims decodeToken(String jwt);
    public Optional<String> save(AuthEntity authEntity);
    public void setClock(Clock clock);
}
