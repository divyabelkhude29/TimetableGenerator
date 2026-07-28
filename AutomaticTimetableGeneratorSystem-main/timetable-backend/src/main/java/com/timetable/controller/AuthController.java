package com.timetable.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timetable.dto.auth.LoginRequest;
import com.timetable.dto.auth.LoginResponse;
import com.timetable.dto.auth.RegisterRequest;

import com.timetable.entity.User;

import com.timetable.jwt.JwtUtil;

import com.timetable.security.CustomUserDetails;

import com.timetable.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {


    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final UserService userService;


    public AuthController(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UserService userService) {

        this.authenticationManager =
                authenticationManager;

        this.jwtUtil =
                jwtUtil;

        this.userService =
                userService;
    }


    /*
     * ============================================================
     * REGISTER
     * ============================================================
     *
     * POST:
     * /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequest request) {

        String response =
                userService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    /*
     * ============================================================
     * LOGIN
     * ============================================================
     *
     * POST:
     * /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        try {

            /*
             * Authenticate username and password
             */
            Authentication authentication =
                    authenticationManager.authenticate(

                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(),
                                    request.getPassword()
                            )
                    );


            /*
             * Get authenticated user
             */
            CustomUserDetails userDetails =
                    (CustomUserDetails)
                            authentication.getPrincipal();


            /*
             * Get User entity
             */
            User user =
                    userDetails.getUser();


            /*
             * Generate JWT
             */
            String token =
                    jwtUtil.generateToken(user);


            /*
             * Create response
             */
            LoginResponse response =
                    new LoginResponse();


            response.setToken(token);

            response.setUsername(
                    user.getUsername()
            );

            response.setRole(
                    user.getRole().name()
            );

            response.setMessage(
                    "Login Successful"
            );


            return ResponseEntity.ok(response);


        } catch (BadCredentialsException ex) {

            throw new BadCredentialsException(
                    "Invalid username or password."
            );


        } catch (DisabledException ex) {

            throw new DisabledException(
                    "User account is disabled."
            );


        } catch (LockedException ex) {

            throw new LockedException(
                    "User account is locked."
            );
        }
    }


    /*
     * ============================================================
     * CURRENT USER PROFILE
     * ============================================================
     *
     * GET:
     * /api/auth/profile
     *
     * Requires JWT.
     */
    @GetMapping("/profile")
    public ResponseEntity<CustomUserDetails> profile(
            Authentication authentication) {

        CustomUserDetails user =
                (CustomUserDetails)
                        authentication.getPrincipal();

        return ResponseEntity.ok(user);
    }

}