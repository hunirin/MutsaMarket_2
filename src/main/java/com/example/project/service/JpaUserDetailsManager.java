package com.example.project.service;

import com.example.project.dto.CustomUserDetails;
import com.example.project.entity.AuthorityEntity;
import com.example.project.entity.RoleEntity;
import com.example.project.entity.UserEntity;
import com.example.project.repository.AuthorityRepository;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.hibernate.cfg.AvailableSettings.USER;

@Slf4j
@Service
public class JpaUserDetailsManager implements UserDetailsManager {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public JpaUserDetailsManager(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository,
            AuthorityRepository authorityRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        // READ 권한 생성
        AuthorityEntity readAuthority = new AuthorityEntity();
        readAuthority.setName("READ");
        readAuthority = authorityRepository.save(readAuthority);

        // WRITE 권한 생성
        AuthorityEntity writeAuthority = new AuthorityEntity();
        writeAuthority.setName("WRITE");
        writeAuthority = authorityRepository.save(writeAuthority);

        // USER 역할 생성 (READ)
        RoleEntity userRole = new RoleEntity();
        userRole.setName("USER");
        userRole.getAuthorities().add(readAuthority);
        userRole = roleRepository.save(userRole);

        // ADMIN 역할 생성 (READ, WRITE)
        RoleEntity adminRole = new RoleEntity();
        adminRole.setName("ADMIN");
        adminRole.getAuthorities().add(readAuthority);
        adminRole.getAuthorities().add(writeAuthority);
        adminRole = roleRepository.save(adminRole);

    }

    @Override
    // 실제로 Spring Security 내부에서 사용하는 반드시 구현해야 정상동작을 기대할 수 있는 메소드
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser
                = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(username);
        return CustomUserDetails.fromEntity(optionalUser.get());
    }

    @Override
    // 새로운 사용자를 저장하는 메소드
    public void createUser(UserDetails user) {
        log.info("try create user: {}", user.getUsername());

        if (this.userExists(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) user;
            UserEntity userEntity = customUserDetails.newEntity();

            RoleEntity userRole = roleRepository.findByName("USER");
            if (userRole == null) {

                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            userEntity.setRoles(Collections.singletonList(userRole));

            userRepository.save(userEntity);
        } catch (ClassCastException e) {
            log.error("failed to cast to {}", CustomUserDetails.class);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    // 계정이름을 가진 사용자가 존재하는지 확인하는 메소드
    public boolean userExists(String username) {
        log.info("check if user: {} exists", username);
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

}