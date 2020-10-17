package com.jwt.provider.controller;

import com.jwt.provider.model.AuthEntity;
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
    public ResponseEntity<String> retrieveJwt(@RequestBody AuthEntity authEntity) {
        if(jwtService.isAuthorized(authEntity)) {
            return new ResponseEntity<>(jwtService.createToken(authEntity.getUser()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/jwt/validate", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<?> validateJwt(@RequestBody String jwt) {
        JSONObject jwtObject = new JSONObject(jwt);
        if(jwtService.isValidToken(jwtObject.getString("jwt"))) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
