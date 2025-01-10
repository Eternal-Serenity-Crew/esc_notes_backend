package com.esc.escnotesbackend.utils;

import com.esc.escnotesbackend.entities.User;
import com.esc.escnotesbackend.exceptions.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET = "${JWT_SECRET}";
    private static final Long EXPIRATION_TIME = 60 * 60 * 24 * 90L;
    private static final Long REFRESH_EXPIRATION_TIME = 60 * 60 * 24 * 24 * 90L;

    private JwtUtil() {}

    public static String generateAuthToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole().toString());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusSeconds(EXPIRATION_TIME)))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusSeconds(REFRESH_EXPIRATION_TIME)))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static boolean validateToken(String token, User user) {
        final String email = getEmailFromToken(token);
        if (email.equals(user.getEmail()) && email.equals(getEmailFromToken(token))) {
            return true;
        } else {
            throw new InvalidTokenException();
        }
    }

    public static String getEmailFromToken(String token) {
        return getClaimsFromToken(token).get("email", String.class);
    }

    public static String getRoleFromToken(String token) {
        return (String) getClaimsFromToken(token).get("role");
    }

    public static boolean isTokenExpired(String token) {
        return getClaimsFromToken(token).getExpiration().before(new Date());
    }

    public static boolean isTokenRefreshable(String token) {
        if (isTokenExpired(token)) throw new InvalidTokenException("Token has expired");

        Long issuedAt = getIssuedAtFromToken(token);
        Long expirationAt = getExpirationTimeFromToken(token);

        long tokenLifeTime = expirationAt - issuedAt;
        long timeLeft = expirationAt - System.currentTimeMillis();

        return timeLeft < (tokenLifeTime / 4);
    }

    private static Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    private static Long getExpirationTimeFromToken(String token) {
        return getClaimsFromToken(token).getExpiration().getTime();
    }

    private static Long getIssuedAtFromToken(String token) {
        return getClaimsFromToken(token).getIssuedAt().getTime();
    }
}
