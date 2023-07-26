package com.example.project.controller;


import com.example.project.dto.ResponseDto;
import com.example.project.dto.UserDto;
//import com.example.project.service.JpaUserDetailsManager;
import com.example.project.jwt.JwtTokenUtils;
import com.example.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@Controller
@RequestMapping("users")
public class UserController {
    // 의존성 주입
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


//    @GetMapping("/login")
//    public ResponseDto login(
//            @RequestBody UserDto userDto
//            ) {
//        ResponseDto response = new ResponseDto();
//        response.setMessage("로그인 되었습니다.");
//        return response;
//    }

    @PostMapping("/login")
    public ResponseDto loginJwt(@RequestBody UserDto userDto) {
        log.info(userDto.toString());
        ResponseDto response = new ResponseDto();
        UserDto authenticatedUser = service.loginJwt(userDto.getUsername(), userDto.getPassword(), userDto);
        response.setMessage("로그인 되었습니다. \n token: " + authenticatedUser.getToken());
        return response;
    }

//    @GetMapping("/my-profile")
//    public String myProfile(
//            Authentication authentication
//    ) {
//        UserDto userDetails
//                = (UserDto) authentication.getPrincipal();
//        log.info(userDetails.getUsername());
//        log.info(userDetails.getEmail());
//        return "my-profile";
//    }

//    @GetMapping("/register")
//    public String registerForm() {
//        return "register-form";
//    }



    @PostMapping("/register")
    public ResponseDto registerPost(
            @RequestBody UserDto userDto
    ) {
        log.info(userDto.toString());
        ResponseDto response = new ResponseDto();
        service.createUser(userDto);
        response.setMessage("회원 가입 되었습니다.");
        return response;
    }
}
