package com.example.project.controller;

import com.example.project.entity.UserEntity;
import com.example.project.exceptions.IncorrectPasswordException;
import com.example.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/view")
@RequiredArgsConstructor
public class ViewController {
    private final UserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

}
//    @PostMapping("/login")
//    public String loginForm(
////            @RequestParam String username,
////            @RequestParam String password
//    ) {
////        Optional<UserEntity> user = userRepository.findByUsername(username);
////
////        if (user.isPresent()) {
////            UserEntity userEntity = user.get();
////            String storedPw = userEntity.getPassword();
////            String storedId = userEntity.getUsername();
////            log.info(storedId);
////            log.info(storedPw);
////
////            if (passwordEncoder.matches(password, storedPw)) {
//                return "items-form";
//            }
////        } else {
////            return "redirect:/login-form.html";
////        }
////    }
