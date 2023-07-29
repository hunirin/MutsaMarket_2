package com.example.project.jwt;

//import com.example.project.dto.UserDto;
import com.example.project.service.JpaUserDetailsManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private JwtTokenUtils jwtTokenUtils;
    private final JpaUserDetailsManager userDetailsManager;

    public JwtTokenFilter(JwtTokenUtils jwtTokenUtils,
                          JpaUserDetailsManager userDetailsManager) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.userDetailsManager = userDetailsManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader
                = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.split(" ")[1];
            if (jwtTokenUtils.validate(token)) {
                SecurityContext context
                        = SecurityContextHolder.createEmptyContext();
                // JWT에서 사용자 이름을 가져오기
                String username = jwtTokenUtils
                        .parseClaims(token)
                        .getSubject();
                // 사용자 인증 정보 생성
                UserDetails userDetails =
                        userDetailsManager.loadUserByUsername(username);

                AbstractAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        token,
                        userDetails.getAuthorities()
                );
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);
                log.info("set security context with jwt");
            }
            else {
                log.warn("jwt validation failed");
            }
        }
        filterChain.doFilter(request, response);
    }
}
