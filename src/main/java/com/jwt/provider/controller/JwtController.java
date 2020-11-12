package com.jwt.provider.controller;

import java.util.Optional;

import com.jwt.provider.model.AuthEntity;
import com.jwt.provider.model.StatusEntity;
import com.jwt.provider.model.TokenEntity;
import com.jwt.provider.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.json.JSONObject;

@RestController
public class JwtController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenEntity tokenEntity;

    @Autowired
    private StatusEntity statusEntity;

    @PostMapping(value = "/jwt/request", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<?> retrieveJwt(@RequestBody AuthEntity authEntity) {
        if(jwtService.isAuthorized(authEntity)) {
            statusEntity.setStatus("Token Retrieval Successful.");
            statusEntity.setMajorCode(0);
            statusEntity.setMinorCode(0);
            tokenEntity.setToken(jwtService.createToken(authEntity.getUserName()));
            tokenEntity.setAuthEntity(authEntity);
            tokenEntity.setStatusEntity(statusEntity);
            return ResponseEntity.ok().body(tokenEntity);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "/jwt/validate", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<?> validateJwt(@RequestBody String token) {
        JSONObject jwtObject = new JSONObject(token);
        if(jwtService.isValidToken(jwtObject.getString("token"))) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "/jwt/add-user", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<?> validateJwt(@RequestBody AuthEntity authEntity) {
        Optional<String> jwt = jwtService.save(authEntity);
        if(jwt.isPresent()) {
            statusEntity.setStatus("User added successfully.");
            statusEntity.setMajorCode(0);
            statusEntity.setMinorCode(0);
            tokenEntity.setToken(jwt.get());
            tokenEntity.setAuthEntity(authEntity);
            tokenEntity.setStatusEntity(statusEntity);
            return ResponseEntity.ok().body(tokenEntity);
        } else {
            statusEntity.setStatus("User added successfully.");
            statusEntity.setMajorCode(0);
            statusEntity.setMinorCode(0);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(statusEntity);
        }
    }
}
