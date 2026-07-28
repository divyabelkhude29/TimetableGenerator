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
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

        /*
         * These endpoints are PUBLIC.
         *
         * JWT should NOT be processed here.
         */
        return path.equals("/auth/login")
                || path.equals("/auth/register")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/webjars");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String requestPath = request.getRequestURI();

        System.out.println("========================================");
        System.out.println("Incoming Request : " + requestPath);

        String authorizationHeader =
                request.getHeader("Authorization");

        /*
         * No Authorization header.
         *
         * Continue normally.
         */
        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token =
                authorizationHeader.substring(7).trim();

        if (token.isEmpty()) {

            filterChain.doFilter(request, response);
            return;
        }

        try {

            /*
             * Extract username from JWT.
             */
            String username =
                    jwtUtil.extractUsername(token);

            System.out.println(
                    "Username From Token : " + username);

            /*
             * Only authenticate if there is no
             * existing authentication.
             */
            if (username != null
                    && SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {

                UserDetails userDetails =
                        customUserDetailsService
                                .loadUserByUsername(username);

                /*
                 * Validate token.
                 */
                boolean valid =
                        jwtUtil.validateToken(
                                token,
                                userDetails.getUsername());

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
                            "JWT Authentication Successful");

                } else {

                    System.out.println(
                            "JWT Token is invalid");
                }
            }

        } catch (ExpiredJwtException ex) {

            /*
             * IMPORTANT:
             *
             * Do not print a full stack trace.
             * Do not stop the filter chain.
             *
             * The request will continue as unauthenticated.
             */
            System.out.println(
                    "JWT Token expired. Request will continue without authentication.");

            SecurityContextHolder.clearContext();

        } catch (JwtException ex) {

            /*
             * Invalid JWT:
             * signature, malformed token, etc.
             */
            System.out.println(
                    "Invalid JWT Token. Request will continue without authentication.");

            SecurityContextHolder.clearContext();

        } catch (Exception ex) {

            /*
             * Prevent JWT errors from crashing
             * the request.
             */
            System.out.println(
                    "JWT authentication failed: "
                            + ex.getMessage());

            SecurityContextHolder.clearContext();
        }

        /*
         * Always continue the request.
         */
        filterChain.doFilter(request, response);
    }
}