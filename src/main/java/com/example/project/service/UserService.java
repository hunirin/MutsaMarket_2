package com.example.project.service;


import com.example.project.dto.UserDto;
import com.example.project.entity.UserEntity;
import com.example.project.jwt.JwtTokenUtils;
import com.example.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    // 회원가입
    public UserDto createUser(UserDto dto) {
        UserEntity newUser = new UserEntity();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(dto.getPassword());
        newUser.setEmail(dto.getEmail());
        return dto.fromEntity(repository.save(newUser));
    }

    // 로그인
    public UserDto loginJwt(String username, String password, UserDto dto) {
        Optional<UserEntity> optionalUser = repository.findByUsername(dto.getUsername());
        if(optionalUser.isEmpty()) throw new UsernameNotFoundException(dto.getUsername());

        UserEntity userEntity = optionalUser.get();
        String pw = userEntity.getPassword();
        String id = userEntity.getUsername();

        if (username.equals(id) && password.equals(pw)) {
            userEntity.setToken(jwtTokenUtils.generateToken(dto));
            return dto.fromEntity(repository.save(userEntity));
        } else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);


    }

}
