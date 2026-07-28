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

    /**
     * Authentication Provider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    /**
     * Authentication Manager
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    /**
     * Security Filter Chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

                // Disable CSRF
                .csrf(csrf -> csrf.disable())

                // Enable CORS
                .cors(Customizer.withDefaults())

                // Stateless Session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Exception Handling
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(authenticationEntryPoint))

                // Authentication Provider
                .authenticationProvider(authenticationProvider())

                // URL Authorization
                .authorizeHttpRequests(auth -> auth

                        /*
                         * Public APIs
                         */
                        .requestMatchers(
                                "/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        /*
                         * User Management
                         */
                        .requestMatchers("/users/**")
                        .hasRole("ADMIN")

                        /*
                         * Student Management
                         */
                        .requestMatchers("/students/**")
                        .hasAnyRole("ADMIN", "STUDENT")

                        /*
                         * Faculty Management
                         */
                        .requestMatchers("/faculties/**")
                        .hasAnyRole("ADMIN", "FACULTY")

                        /*
                         * All Academic Master Modules
                         */
                        .requestMatchers(
                                "/departments/**",
                                "/courses/**",
                                "/academic-years/**",
                                "/semesters/**",
                                "/subjects/**",
                                "/working-days/**",
                                "/time-slots/**",
                                "/academic-sections/**",
                                "/faculty-subject-allocation/**",
                                "/faculty-availability/**",
                                "/subject-workloads/**",
                                "/holidays/**",
                                "/timetable-constraints/**",
                                "/reports/**"
                        )
                        .hasRole("ADMIN")

                        /*
                         * Timetable
                         */
                        .requestMatchers("/timetables/**")
                        .authenticated()

                        /*
                         * Everything Else
                         */
                        .anyRequest()
                        .authenticated())

                // JWT Filter
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}