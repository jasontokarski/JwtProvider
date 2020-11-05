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

    @PostMapping(value = "/jwt/request", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<JSONObject> retrieveJwt(@RequestBody AuthEntity authEntity) {
        if(jwtService.isAuthorized(authEntity)) {
            JSONObject jwtObject = new JSONObject();
            jwtObject.put("token", jwtService.createToken(authEntity.getUserName()));
            return new ResponseEntity<>(jwtObject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/jwt/validate", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<?> validateJwt(@RequestBody String jwt) {
        JSONObject jwtObject = new JSONObject(jwt);
        if(jwtService.isValidToken(jwtObject.getString("token"))) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/jwt/add-user", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<?> validateJwt(@RequestBody AuthEntity authEntity) {
        Optional<String> jwt = jwtService.save(authEntity);
        if(jwt.isPresent()) {
            return new ResponseEntity<>(new TokenEntity(jwt.get(), authEntity, new StatusEntity("User added successfully.", 0 ,0)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new StatusEntity("User already exists.", 0 , 2), HttpStatus.CONFLICT);
        }
    }
}
