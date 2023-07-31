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
                                        "/items/{id}/comments/read",
                                        "/items/{id}/proposal/read",
                                        "/view/login",
                                        "/resources/**"
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
                                .defaultSuccessUrl("/view/items-form")
                                // 로그인 실패시 이동하는 페이지(경로)
                                .failureUrl("/view/login?fail")
                                // 로그인 과정에서 필요한 경로들을
                                // 모든 사용자가 사용할 수 있게끔 권한설정
                                .permitAll()
                )
                // 로그아웃 관련 설정
                // 로그인 -> 쿠키를 통해 세션을 생성
                //          아이디와 비밀번호
                // 로그아웃 -> 세션을 제거
                //            세션 정보만 있으면 제거 가능
                .logout(
                        logout -> logout
                                // 로그아웃 요청을 보낼 URL
                                // 어떤 UI에 로그아웃 기능을 연결하고 싶으면
                                // 해당 UI가 /users/logout으로 POST 요청을
                                // 보내게끔
                                .logoutUrl("/view/logout")
                                // 로그아웃 성공시 이동할 URL 설정
                                .logoutSuccessUrl("/view/login")
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

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 자원에 스프링 시큐리티 필터 규칙을 적용하지 않도록 설정
        return (web) -> web.ignoring().requestMatchers("/static/**");
    }
}
