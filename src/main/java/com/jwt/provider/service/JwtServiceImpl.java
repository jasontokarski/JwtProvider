package com.jwt.provider.service;

import java.time.Clock;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.jwt.provider.model.AuthEntity;
import com.jwt.provider.repository.JwtRepository;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service("jwtService")
public class JwtServiceImpl implements JwtService {

    @Autowired
    private JwtRepository jwtRepository;

    @Autowired
    private Clock clock;

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    @Value("${jwt.issuer.key}")
    private String jwtIssuerKey;

    @Value("${jwt.ttl}")
    private long ttl;
    
    @Override
    public Optional<AuthEntity> findByUser(String user) {
        return jwtRepository.findByUserName(user);
    }

    @Override
    public String createToken(String subject) {

        Date now = new Date(clock.instant().toEpochMilli());
        Date expiryDate = new Date(clock.instant().toEpochMilli() + ttl);
        byte[] apiKeySecretBytes = Base64.decodeBase64(jwtSecretKey);
        SecretKey signingKey = new SecretKeySpec(apiKeySecretBytes, "HS256");

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setIssuer(jwtIssuerKey)
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .setExpiration(expiryDate)
                .compact();
    }

    public Claims decodeToken(String jwt) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException {
        Claims claims = Jwts.parser()
                .setSigningKey(Base64.decodeBase64(jwtSecretKey))
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

    public boolean isAuthorized(AuthEntity authEntity) {
        if(authEntity.getApiKey() == null || authEntity.getApiKey().isEmpty()) {
            return false;
        }
        Optional<AuthEntity> authEntityLookup = findByUser(authEntity.getUserName());
        return (authEntityLookup.isPresent() && authEntity.getApiKey().equals(authEntityLookup.get().getApiKey()));
    }

    public boolean isValidToken(String jwt) {
        try {
            decodeToken(jwt);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<String> save(AuthEntity authEntity) {
        try {
            jwtRepository.save(authEntity);
        } catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            return Optional.ofNullable(null);
        }
        return Optional.of(createToken(authEntity.getUserName()));
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
