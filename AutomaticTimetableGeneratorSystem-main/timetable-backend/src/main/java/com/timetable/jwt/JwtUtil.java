package com.timetable.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.timetable.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @PostConstruct
    public void init() {

        System.out.println("====================================");
        System.out.println("JWT INITIALIZATION");
        System.out.println("====================================");

        System.out.println("JWT Secret Length : "
                + jwtSecret.length());

        System.out.println("JWT Expiration : "
                + jwtExpiration + " milliseconds");

        System.out.println("JWT Expiration : "
                + (jwtExpiration / 1000) + " seconds");

        System.out.println("JWT Expiration : "
                + (jwtExpiration / 1000 / 60) + " minutes");

        System.out.println("JWT Expiration : "
                + (jwtExpiration / 1000 / 60 / 60) + " hours");

        System.out.println("Current Server Time : "
                + new Date());

        System.out.println("====================================");
    }

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                jwtSecret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(User user) {

        Date now = new Date();

        Date expiration =
                new Date(now.getTime() + jwtExpiration);

        Map<String, Object> claims =
                new HashMap<>();

        claims.put(
                "role",
                user.getRole().name()
        );

        claims.put(
                "userId",
                user.getUserId()
        );

        String token = Jwts.builder()

                .claims(claims)

                .subject(
                        user.getUsername()
                )

                .issuedAt(now)

                .expiration(expiration)

                .signWith(
                        getSigningKey()
                )

                .compact();

        System.out.println("====================================");
        System.out.println("JWT GENERATED");
        System.out.println("====================================");

        System.out.println(
                "Username : "
                + user.getUsername()
        );

        System.out.println(
                "User ID : "
                + user.getUserId()
        );

        System.out.println(
                "Role : "
                + user.getRole().name()
        );

        System.out.println(
                "Issued At : "
                + now
        );

        System.out.println(
                "Expiration : "
                + expiration
        );

        System.out.println(
                "Valid For : "
                + jwtExpiration
                + " milliseconds"
        );

        System.out.println("====================================");

        return token;
    }

    public String extractUsername(
            String token) {

        return extractClaim(
                token,
                Claims::getSubject
        );
    }

    public Date extractExpiration(
            String token) {

        return extractClaim(
                token,
                Claims::getExpiration
        );
    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> resolver) {

        Claims claims =
                extractAllClaims(token);

        return resolver.apply(claims);
    }

    private Claims extractAllClaims(
            String token) {

        return Jwts.parser()

                .verifyWith(
                        getSigningKey()
                )

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }

    public boolean isTokenExpired(
            String token) {

        Date expiration =
                extractExpiration(token);

        boolean expired =
                expiration.before(
                        new Date()
                );

        System.out.println(
                "JWT Expiration : "
                + expiration
        );

        System.out.println(
                "Current Time : "
                + new Date()
        );

        System.out.println(
                "Token Expired : "
                + expired
        );

        return expired;
    }

    public boolean validateToken(
            String token,
            String username) {

        try {

            String extractedUsername =
                    extractUsername(token);

            boolean usernameMatches =
                    extractedUsername.equals(
                            username
                    );

            boolean expired =
                    isTokenExpired(token);

            System.out.println(
                    "JWT Username : "
                    + extractedUsername
            );

            System.out.println(
                    "Requested Username : "
                    + username
            );

            System.out.println(
                    "Username Matches : "
                    + usernameMatches
            );

            System.out.println(
                    "Token Expired : "
                    + expired
            );

            return usernameMatches
                    && !expired;

        } catch (JwtException ex) {

            System.out.println(
                    "JWT Validation Error : "
                    + ex.getMessage()
            );

            return false;

        } catch (Exception ex) {

            System.out.println(
                    "JWT Validation Exception : "
                    + ex.getMessage()
            );

            return false;
        }
    }
}