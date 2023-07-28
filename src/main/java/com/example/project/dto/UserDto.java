//package com.example.project.dto;
//
//
//import com.example.project.entity.UserEntity;
//import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//
//
////@Builder
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class UserDto  {
////    @Getter
//    private Long id;
//    private String username;
//    private String password;
////    @Getter
//    private String email;
////    @Getter
//    private String phone;
////    @Getter
//    private String address;
//
////    @Getter
//    private String token;
//
////    @Override
////    public Collection<? extends GrantedAuthority> getAuthorities() {
////        return null;
////    }
////
////    @Override
////    public String getPassword() {
////        return this.password;
////    }
////
////    @Override
////    public String getUsername() {
////        return this.username;
////    }
////
////
////    @Override
////    public boolean isAccountNonExpired() {
////        return true;
////    }
////
////    @Override
////    public boolean isAccountNonLocked() {
////        return true;
////    }
////
////    @Override
////    public boolean isCredentialsNonExpired() {
////        return true;
////    }
////
////    @Override
////    public boolean isEnabled() {
////        return true;
////    }
//
//    public static UserDto fromEntity(UserEntity entity) {
////        return UserDto.builder()
////                .id(entity.getId())
////                .username(entity.getUsername())
////                .password(entity.getPassword())
////                .email(entity.getEmail())
////                .token(entity.getToken())
////                .build();
//        UserDto dto = new UserDto();
//        dto.setId(entity.getId());
//        dto.setUsername(entity.getUsername());
//        dto.setPassword(entity.getPassword());
//        dto.setAddress(dto.getAddress());
//        dto.setEmail(dto.getEmail());
//        dto.setPhone(dto.getPhone());
//        dto.setToken(dto.getToken());
//        return dto;
//    }
//
////    public UserEntity newEntity() {
////        UserEntity entity = new UserEntity();
////        entity.setUsername(username);
////        entity.setPassword(password);
////        entity.setEmail(email);
////        entity.setToken(token);
////        return entity;
////    }
//
////    @Override
////    public String toString() {
////        return "CustomUserDetails{" +
////                "id=" + id +
////                ", username='" + username + '\'' +
////                ", password='[PROTECTED]'" +
////                ", email='" + email + '\'' +
////                '}';
////    }
//}
