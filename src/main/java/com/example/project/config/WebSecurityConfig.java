package com.example.project.config;

//import com.example.project.jwt.JwtTokenFilter;
import com.example.project.jwt.JwtTokenFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;

    public WebSecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authHttp -> authHttp
                                .requestMatchers(
                                        "/no-auth",
                                        "/token/issue",
                                        "/users/register",
                                        "/items/read",
                                        "/items/read/**",
                                        "/items/{id}/comments/read",
                                        "/items/{id}/proposal/read",
                                        "/view/login",
                                        "/resources/**",
                                        "/view/register"
                                )
                                .permitAll()
//                                .requestMatchers(
//                                        "/**"
//                                )
//                                .hasRole("USER")
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(
                        formLogin -> formLogin
                                // 로그인 하는 페이지(경로)를 지정
                                .loginPage("/view/login")
                                // 로그인 성공시 이동하는 페이지(경로)
                                .successHandler((request, response, authentication) -> {
                                    response.sendRedirect("/view/home"); // Redirect to your desired URL
                                })
//                                .defaultSuccessUrl("/view/my-profile")
                                // 로그인 실패시 이동하는 페이지(경로)
                                .failureUrl("/view/login?fail")
//                                .usernameParameter("username")
//                                .passwordParameter("password")
                                // 로그인 과정에서 필요한 경로들을
                                // 모든 사용자가 사용할 수 있게끔 권한설정
                                .permitAll()
                )
                .logout(
                        logout -> logout
                                .logoutUrl("/view/logout")
                                .logoutSuccessUrl("/view/login")
                )
//                .sessionManagement(
//                        sessionManagement -> sessionManagement
//                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
                .addFilterBefore(
                        jwtTokenFilter,
                        AuthorizationFilter.class
                )
        ;

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 자원에 스프링 시큐리티 필터 규칙을 적용하지 않도록 설정
        return (web) -> web.ignoring().requestMatchers("/static/**");
    }
}
