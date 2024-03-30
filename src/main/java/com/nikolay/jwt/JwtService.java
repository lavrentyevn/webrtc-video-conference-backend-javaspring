package com.nikolay.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${application.security.jwt.expiration}")
    private long accessExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;
    @Value("${application.security.jwt.verification-token.expiration}")
    private long verificationExpiration;

    public String extractEmail(String token) {
        return (String) extractAllClaims(token).get("email");
    }

    public String extractUsername(String token) {
        return (String) extractAllClaims(token).get("username");
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }


    public String generateVerificationToken(Map<String, Object> extraClaims) {
        return buildToken(extraClaims, verificationExpiration);
    }

    public String generateAccessToken(Map<String, Object> extraClaims) {
        return buildToken(extraClaims, accessExpiration);
    }


    public String generateRefreshToken(Map<String, Object> extraClaims) {
        return buildToken(extraClaims, refreshExpiration);
    }

    public String buildToken(Map<String, Object> extraClaims, long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return (extractEmail(token) != null || extractUsername(token).equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

}
