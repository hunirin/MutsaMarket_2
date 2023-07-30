package com.example.project.controller;


import com.example.project.jwt.JwtRequestDto;
import com.example.project.jwt.JwtTokenDto;
import com.example.project.jwt.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/token")
public class TokenController {
    // UserDetailsManager: 사용자 정보 회수
    // PasswordEncoder: 비밀번호 대조용 인코더
    private final UserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtils jwtTokenUtils;

    public TokenController(
            UserDetailsManager manager,
            PasswordEncoder passwordEncoder,
            JwtTokenUtils jwtTokenUtils
    ) {
        this.manager = manager;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    // /token/issue: JWT 발급용 Endpoint
    @PostMapping("/issue")
    public JwtTokenDto issueJwt(@RequestBody JwtRequestDto dto) {
        // 사용자 정보 회수
        UserDetails userDetails
                = manager.loadUserByUsername(dto.getUsername());
        if (!passwordEncoder.matches(dto.getPassword(), userDetails.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        JwtTokenDto response = new JwtTokenDto();
        response.setToken(jwtTokenUtils.generateToken(userDetails));
        return response;
    }

}