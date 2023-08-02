package com.example.project.controller;


import com.example.project.dto.CustomUserDetails;
import com.example.project.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;


    public UserController(
            UserDetailsManager manager,
            PasswordEncoder passwordEncoder
    ) {
        this.manager = manager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseDto registerPost(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("password-check") String passwordCheck
    ) {
        ResponseDto response = new ResponseDto();
        // 비밀번호가 일치할 시 UserDetailsManager를 통해 유저 등록
        if (password.equals(passwordCheck)) {
            log.info("password match!");

            manager.createUser(CustomUserDetails.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .build());

            response.setMessage("가입이 완료되었습니다.");
            return response;
        }
        log.warn("password does not match...");
        response.setMessage("입력한 정보가 틀렸습니다.");
        return response;

    }
}
