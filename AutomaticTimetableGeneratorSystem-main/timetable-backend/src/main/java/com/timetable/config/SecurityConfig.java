package com.timetable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.timetable.jwt.JwtAuthenticationEntryPoint;
import com.timetable.jwt.JwtFilter;
import com.timetable.security.CustomUserDetailsService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    private final CustomUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;


    public SecurityConfig(
            JwtFilter jwtFilter,
            JwtAuthenticationEntryPoint authenticationEntryPoint,
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        this.jwtFilter = jwtFilter;

        this.authenticationEntryPoint = authenticationEntryPoint;

        this.userDetailsService = userDetailsService;

        this.passwordEncoder = passwordEncoder;
    }


    /*
     * ============================================================
     * AUTHENTICATION PROVIDER
     * ============================================================
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(
                userDetailsService
        );

        provider.setPasswordEncoder(
                passwordEncoder
        );

        return provider;
    }


    /*
     * ============================================================
     * AUTHENTICATION MANAGER
     * ============================================================
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }


    /*
     * ============================================================
     * SECURITY FILTER CHAIN
     * ============================================================
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        /*
         * Disable CSRF
         */
        http.csrf(csrf -> csrf.disable());


        /*
         * Enable CORS
         */
        http.cors(Customizer.withDefaults());


        /*
         * Stateless session
         */
        http.sessionManagement(session ->
                session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )
        );


        /*
         * Exception handling
         */
        http.exceptionHandling(exception ->
                exception.authenticationEntryPoint(
                        authenticationEntryPoint
                )
        );


        /*
         * Authentication provider
         */
        http.authenticationProvider(
                authenticationProvider()
        );


        /*
         * URL authorization
         */
        http.authorizeHttpRequests(auth -> auth

                /*
                 * ==================================================
                 * PUBLIC AUTHENTICATION APIs
                 * ==================================================
                 *
                 * No JWT required.
                 *
                 * POST /api/auth/login
                 * POST /api/auth/register
                 */
                .requestMatchers(
                		"/api/auth/admin-exists",
                        "/api/auth/login",
                        "/api/auth/register"
                )
                .permitAll()


                /*
                 * ==================================================
                 * SWAGGER
                 * ==================================================
                 */
                .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**"
                )
                .permitAll()


                /*
                 * ==================================================
                 * USER MANAGEMENT
                 * ==================================================
                 *
                 * ADMIN only.
                 */
                .requestMatchers(
                        "/api/users/**"
                )
                .hasRole("ADMIN")


                /*
                 * ==================================================
                 * STUDENT MANAGEMENT
                 * ==================================================
                 *
                 * ADMIN and STUDENT.
                 */
                .requestMatchers(
                        "/api/students/**"
                )
                .hasAnyRole(
                        "ADMIN",
                        "STUDENT"
                )


                /*
                 * ==================================================
                 * FACULTY MANAGEMENT
                 * ==================================================
                 *
                 * ADMIN and FACULTY.
                 */
                .requestMatchers(
                        "/api/faculties/**"
                )
                .hasAnyRole(
                        "ADMIN",
                        "FACULTY"
                )


                /*
                 * ==================================================
                 * ADMIN MASTER MODULES
                 * ==================================================
                 */
                .requestMatchers(
                        "/api/departments/**",
                        "/api/courses/**",
                        "/api/academic-years/**",
                        "/api/semesters/**",
                        "/api/subjects/**",
                        "/api/working-days/**",
                        "/api/time-slots/**",
                        "/api/academic-sections/**",
                        "/api/faculty-subject-allocation/**",
                        "/api/faculty-availability/**",
                        "/api/subject-workloads/**",
                        "/api/holidays/**",
                        "/api/timetable-constraints/**",
                        "/api/reports/**"
                )
                .hasRole("ADMIN")


                /*
                 * ==================================================
                 * TIMETABLE
                 * ==================================================
                 *
                 * Any authenticated user.
                 */
                .requestMatchers(
                        "/api/timetables/**"
                )
                .authenticated()


                /*
                 * ==================================================
                 * EVERYTHING ELSE
                 * ==================================================
                 */
                .anyRequest()
                .authenticated()
        );


        /*
         * ============================================================
         * JWT FILTER
         * ============================================================
         *
         * JWT filter runs before UsernamePasswordAuthenticationFilter.
         */
        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
        );


        /*
         * Return SecurityFilterChain
         */
        return http.build();
    }
}