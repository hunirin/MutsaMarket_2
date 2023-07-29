package com.example.project.config;

//import com.example.project.jwt.JwtTokenFilter;
import com.example.project.jwt.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    public WebSecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable();
        http
                // CSRF: Cross Site Request Forgery
                .csrf(AbstractHttpConfigurer::disable)
                // 1. requestMatchers를 통해 설정할 URL 지정
                // 2. permitAll(), authenticated() 등을 통해 어떤 사용자가
                //    접근 가능한지 설정
                .authorizeHttpRequests(
                        authHttp -> authHttp // HTTP 요청 허가 관련 설정을 하고 싶다.
                                // requestMatchers == 어떤 URL로 오는 요청에 대하여 설정하는지
                                // permitAll() == 누가 요청해도 허가한다.
                                .requestMatchers(
                                        "/no-auth",
                                        "/token/issue",
                                        "/users/register",
                                        "/items/read",
                                        "/items/read/**",
                                        "/items/{id}/comments/read"
                                )
                                .permitAll()
                                .requestMatchers(
                                        "/**"
                                )
                                .hasRole("USER")
                                .anyRequest()
                                .permitAll() // 설정 어떻게하는지 헷갈림
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(
                        jwtTokenFilter,
                        AuthorizationFilter.class
                )
        ;

        return http.build();
    }
}
