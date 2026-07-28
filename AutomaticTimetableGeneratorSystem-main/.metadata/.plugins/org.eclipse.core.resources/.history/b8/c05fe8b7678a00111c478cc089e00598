package com.timetable.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.timetable.security.CustomUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtFilter(
            JwtUtil jwtUtil,
            CustomUserDetailsService customUserDetailsService) {

        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("========================================");
        System.out.println("Incoming Request : " + request.getRequestURI());

        String authorizationHeader =
                request.getHeader("Authorization");

        System.out.println("Authorization Header : "
                + authorizationHeader);

        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {

            System.out.println("No Bearer Token Found.");

            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        System.out.println("Received Token :");
        System.out.println(token);

        try {

            String username =
                    jwtUtil.extractUsername(token);

            System.out.println("Username From Token : "
                    + username);

            if (username != null
                    && SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {

                UserDetails userDetails =
                        customUserDetailsService
                                .loadUserByUsername(username);

                System.out.println("Loaded User : "
                        + userDetails.getUsername());

                System.out.println("Authorities : "
                        + userDetails.getAuthorities());

                boolean valid =
                        jwtUtil.validateToken(
                                token,
                                userDetails.getUsername());

                System.out.println("Token Valid : "
                        + valid);

                if (valid) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);

                    System.out.println(
                            "Authentication Stored Successfully.");
                }
            }

        } catch (ExpiredJwtException ex) {

            ex.printStackTrace();

        } catch (MalformedJwtException ex) {

            ex.printStackTrace();

        } catch (SignatureException ex) {

            ex.printStackTrace();

        } catch (UnsupportedJwtException ex) {

            ex.printStackTrace();

        } catch (JwtException ex) {

            ex.printStackTrace();

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}