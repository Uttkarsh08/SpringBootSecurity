package com.uttkarsh.SpringBoot.Security.services;

import com.uttkarsh.SpringBoot.Security.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UserEntity userEntity){
        return Jwts.builder()
                .subject(userEntity.getId().toString())
                .claim("email", userEntity.getEmail())
                .claim("roles", userEntity.getRoles().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*20*60*60))
                .signWith(getSecretKey())
                .compact();
    }
    public String generateRefreshToken(UserEntity userEntity){
        return Jwts.builder()
                .subject(userEntity.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*60))
                .signWith(getSecretKey())
                .compact();
    }


    public Long getUserIdWithJwtToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());

    }
}
