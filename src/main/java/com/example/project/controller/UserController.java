package com.example.project.controller;


import com.example.project.dto.CustomUserDetails;
import com.example.project.dto.ResponseDto;
//import com.example.project.dto.UserDto;
import com.example.project.service.JpaUserDetailsManager;
import com.example.project.jwt.JwtTokenUtils;
//import com.example.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    // 의존성 주입
//    private final UserService service;
//
//    public UserController(UserService service) {
//        this.service = service;
//    }
    private final UserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;


    public UserController(
            UserDetailsManager manager,
            PasswordEncoder passwordEncoder
    ) {
        this.manager = manager;
        this.passwordEncoder = passwordEncoder;
    }

//    @PostMapping("/login")
//    public ResponseDto loginJwt(@RequestBody UserDto userDto) {
//        log.info(userDto.toString());
//        ResponseDto response = new ResponseDto();
//        UserDto authenticatedUser = service.loginJwt(userDto.getUsername(), userDto.getPassword(), userDto);
//        response.setMessage("로그인 되었습니다. \n token: " + authenticatedUser.getToken());
//        return response;
//    }


//    @PostMapping("/register")
//    public ResponseDto registerPost(
//            @RequestBody UserDto userDto
//    ) {
//        log.info(userDto.toString());
//        ResponseDto response = new ResponseDto();
//        service.createUser(userDto);
//        response.setMessage("회원 가입 되었습니다.");
//        return response;
//    }
    @PostMapping("/register")
    public ResponseDto registerPost(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("password-check") String passwordCheck
    ) {
        ResponseDto response = new ResponseDto();

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
