package com.example.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .formLogin().disable()
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(
                                        "/users/login",
                                        "/users/register")
                                .permitAll()
                                .anyRequest()
                                .authenticated()); // 인증이 된 사용자만 허가

        return http.build();
    }
}
