package com.hotelbooking.hotel_service.config;

import com.hotelbooking.hotel_service.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // Require authentication for modifying hotel or room data
                        .requestMatchers(HttpMethod.POST, "/api/hotels/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/hotels/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/hotels/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/api/rooms/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/rooms/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/rooms/**").authenticated()

                        // Allow all other requests (GET, Swagger UI, etc.)
                        .anyRequest().permitAll()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint())
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("""
                    {
                        "error": "Unauthorized",
                        "message": "Authentication is required to access this resource."
                    }
                    """);
        };
    }
}
