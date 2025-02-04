package com.example.StockManagement.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import com.example.StockManagement.data.model.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;
@Service
public class JwtService {

    private final String SECRET_KEY = "12598778c7c58301dc707bd60e2d1365d8953e8ba1f87advjjy4";

    public String extractUsername(String accessToken) {
        return extractClaim(accessToken, Claims::getSubject);
    }

    public boolean isTokenValid(String accessToken, String username) {
        return extractUsername(accessToken).equals(username) && !isTokenExpired(accessToken);
    }

    public boolean isRefreshTokenValid(String accessToken, String username) {
        return isTokenValid(accessToken, username);
    }

    private boolean isTokenExpired(String accessToken) {
        return extractExpiration(accessToken).before(new Date());
    }

    private Date extractExpiration(String accessToken) {
        return extractClaim(accessToken, Claims::getExpiration);
    }

    public <T> T extractClaim(String accessToken, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(accessToken));
    }

    private Claims extractAllClaims(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .setAllowedClockSkewSeconds(60) // Allow 60 seconds of clock skew
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSigninKey())
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // will last 7 days
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}