package com.jnrptt.reservasapp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.time.Clock;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public JwtService jwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expirationMillis,
            Clock clock
    ) {
        return new JwtService(secret, expirationMillis, clock);
    }

    @Bean
    public JwtFilter jwtFilter(JwtService jwtService) {
        return new JwtFilter(jwtService);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint();
    }

    @Bean
    public org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
        return username -> {
            throw new org.springframework.security.core.userdetails.UsernameNotFoundException(
                    "No se gestionan usuarios en memoria"
            );
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtFilter jwtFilter,
            AuthenticationEntryPoint authenticationEntryPoint
    ) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
