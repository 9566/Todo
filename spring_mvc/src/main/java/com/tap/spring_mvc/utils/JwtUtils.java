package com.tap.spring_mvc.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private final String SECRET = "like , share and subscribe .linkedi , insteed . our country .Code io - Tamil";
    private final long EXPIRATION = 1000 * 60  *60;
    private final Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(secretKey, SignatureAlgorithm.HS256)  // toaken seal
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)    // token unseal
                .build()
                .parseClaimsJws(token)  //cheak it valid token or not
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            extractEmail(token);
            return true;
        }
        catch (JwtException exception) {
            return false;
        }
    }

}



