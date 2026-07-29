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
        this.customUserDetailsService =
                customUserDetailsService;
    }

    /**
     * Public endpoints.
     *
     * JWT filter will NOT run for these URLs.
     */
    @Override
    protected boolean shouldNotFilter(
            HttpServletRequest request) {

        String path = request.getServletPath();

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

        String requestPath =
                request.getRequestURI();

        System.out.println(
                "========================================");

        System.out.println(
                "Incoming Request : "
                + requestPath);

        // =====================================================
        // 1. Get Authorization Header
        // =====================================================

        String authorizationHeader =
                request.getHeader("Authorization");

        System.out.println(
                "Authorization Header Present : "
                + (authorizationHeader != null));

        // =====================================================
        // 2. Check Authorization Header
        // =====================================================

        if (authorizationHeader == null
                || authorizationHeader.isBlank()) {

            System.out.println(
                    "No Authorization header found.");

            filterChain.doFilter(
                    request,
                    response);

            return;
        }

        // =====================================================
        // 3. Check Bearer Token
        // =====================================================

        if (!authorizationHeader.startsWith("Bearer ")) {

            System.out.println(
                    "Authorization header does not "
                    + "start with Bearer.");

            filterChain.doFilter(
                    request,
                    response);

            return;
        }

        // =====================================================
        // 4. Extract JWT
        // =====================================================

        String token =
                authorizationHeader
                        .substring(7)
                        .trim();

        if (token.isEmpty()) {

            System.out.println(
                    "JWT token is empty.");

            filterChain.doFilter(
                    request,
                    response);

            return;
        }

        System.out.println(
                "JWT Token Received.");

        try {

            // =================================================
            // 5. Extract Username
            // =================================================

            String username =
                    jwtUtil.extractUsername(token);

            System.out.println(
                    "Username From Token : "
                    + username);

            // =================================================
            // 6. Check Existing Authentication
            // =================================================

            if (username != null
                    && SecurityContextHolder
                    .getContext()
                    .getAuthentication() == null) {

                // =============================================
                // 7. Load User From Database
                // =============================================

                UserDetails userDetails =
                        customUserDetailsService
                                .loadUserByUsername(
                                        username);

                System.out.println(
                        "User Loaded : "
                        + userDetails.getUsername());

                System.out.println(
                        "User Authorities : "
                        + userDetails.getAuthorities());

                // =============================================
                // 8. Validate JWT
                // =============================================

                boolean valid =
                        jwtUtil.validateToken(
                                token,
                                userDetails
                                        .getUsername());

                System.out.println(
                        "JWT Validation Result : "
                        + valid);

                // =============================================
                // 9. Create Authentication
                // =============================================

                if (valid) {

                    UsernamePasswordAuthenticationToken
                            authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails
                                            .getAuthorities());

                    // =========================================
                    // 10. Add Request Details
                    // =========================================

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));

                    // =========================================
                    // 11. Set Security Context
                    // =========================================

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(
                                    authentication);

                    System.out.println(
                            "JWT Authentication Successful.");

                    System.out.println(
                            "Authenticated User : "
                            + username);

                    System.out.println(
                            "Authorities : "
                            + userDetails
                                    .getAuthorities());

                } else {

                    System.out.println(
                            "JWT Validation Failed.");

                    SecurityContextHolder
                            .clearContext();
                }

            } else if (username == null) {

                System.out.println(
                        "Username could not be extracted "
                        + "from JWT.");

            } else {

                System.out.println(
                        "SecurityContext already "
                        + "contains authentication.");
            }

        } catch (ExpiredJwtException ex) {

            // =================================================
            // JWT EXPIRED
            // =================================================

            System.out.println(
                    "========================================");

            System.out.println(
                    "JWT TOKEN EXPIRED");

            System.out.println(
                    "Expiration : "
                    + ex.getClaims()
                        .getExpiration());

            System.out.println(
                    "Current Time : "
                    + new java.util.Date());

            System.out.println(
                    "========================================");

            SecurityContextHolder
                    .clearContext();

        } catch (JwtException ex) {

            // =================================================
            // INVALID JWT
            // =================================================

            System.out.println(
                    "========================================");

            System.out.println(
                    "INVALID JWT TOKEN");

            System.out.println(
                    "Exception Type : "
                    + ex.getClass()
                        .getName());

            System.out.println(
                    "Exception Message : "
                    + ex.getMessage());

            System.out.println(
                    "========================================");

            SecurityContextHolder
                    .clearContext();

        } catch (Exception ex) {

            // =================================================
            // OTHER ERROR
            // =================================================

            System.out.println(
                    "========================================");

            System.out.println(
                    "JWT AUTHENTICATION ERROR");

            System.out.println(
                    "Exception Type : "
                    + ex.getClass()
                        .getName());

            System.out.println(
                    "Exception Message : "
                    + ex.getMessage());

            System.out.println(
                    "========================================");

            ex.printStackTrace();

            SecurityContextHolder
                    .clearContext();
        }

        // =====================================================
        // 12. Continue Filter Chain
        // =====================================================

        filterChain.doFilter(
                request,
                response);
    }
}