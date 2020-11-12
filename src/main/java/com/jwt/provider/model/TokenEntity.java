package com.jwt.provider.model;

public class TokenEntity {
    private String token;
    private AuthEntity authEntity;
    private StatusEntity statusEntity;

    public TokenEntity() {
    }

    public TokenEntity(String token, AuthEntity authEntity, StatusEntity statusEntity) {
        this.token = token;
        this.authEntity = authEntity;
        this.statusEntity = statusEntity;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthEntity getAuthEntity() {
        return this.authEntity;
    }

    public void setAuthEntity(AuthEntity authEntity) {
        this.authEntity = authEntity;
    }

    public StatusEntity getStatusEntity() {
        return this.statusEntity;
    }

    public void setStatusEntity(StatusEntity statusEntity) {
        this.statusEntity = statusEntity;
    }

}
