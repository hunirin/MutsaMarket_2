package com.example.project.controller;

import com.example.project.dto.CustomUserDetails;
import com.example.project.entity.ItemEntity;
import com.example.project.jwt.JwtTokenDto;
import com.example.project.jwt.JwtTokenUtils;
import com.example.project.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Controller
@RequestMapping("/view")
@RequiredArgsConstructor
public class ViewController {
    private final UserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final ItemRepository itemRepository;



    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    @PostMapping("/login")
    public JwtTokenDto loginJwt(
            @RequestBody CustomUserDetails dto

    ) {
        UserDetails userDetails = manager.loadUserByUsername(dto.getUsername());

        if (!passwordEncoder.matches(dto.getPassword(), userDetails.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String token = jwtTokenUtils.generateToken(userDetails);

        JwtTokenDto response = new JwtTokenDto();
        response.setToken(token);
        log.info(response.getToken());
        return response;

    }

    @GetMapping("/register")
    public String registerForm() {
        return "register-form";
    }

    @PostMapping("/register")
    public String registerPost(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("password-check") String passwordCheck,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("address") String address
    ) {
        // TODO 사용자가 입력한 아이디 비밀번호 비밀번호 확인 값을 확인해보세요.
        if (password.equals(passwordCheck)) {
            log.info("password match!");
            manager.createUser(CustomUserDetails.builder()
                            .username(username)
                            .password(passwordEncoder.encode(password))
                            .phone(phone)
                            .email(email)
                            .address(address)
                            .build());
            return "redirect:/view/login";
        }
        log.warn("password does not match...");
        return "redirect:/view/register?error";
    }

    @GetMapping("/home")
    public String itemsHome() {
        return "index";
    }

    @GetMapping("/write")
    public String itemWrite() {
        return "write";
    }

    @PostMapping("/write")
    public String itemPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("minPrice") String minPrice,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        ItemEntity newItem = new ItemEntity();
        newItem.setTitle(title);
        newItem.setContent(content);
        newItem.setMinPrice(minPrice);
        newItem.setUsername(username);

        itemRepository.save(newItem);

        System.out.println("title " + title);
        System.out.println("content " + content);
        System.out.println("minPrice " + minPrice);
        System.out.println("username " + username);

        return "redirect:/view/home" ;
    }

    @GetMapping("/itemsList/{id}")
    public String itemsOne(@PathVariable("id") Long id, Model model) {
        ItemEntity itemEntity = itemRepository.findById(id).orElse(null);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        model.addAttribute(username);
        model.addAttribute("item", itemEntity);

        return "itemsList";
    }

    @GetMapping("/itemsList")
    public String itemsList(
            Model model,
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false, defaultValue = "") String searchText
    ) {

        Page<ItemEntity> items = itemRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, items.getPageable().getPageNumber() - 1);
        int endPage = Math.min(items.getTotalPages(), items.getPageable().getPageNumber() + 3);


        model.addAttribute("items", items);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "itemsList";
    }

    @GetMapping("/itemOne/{id}")
    public String itemTest(@PathVariable("id") Long id, Model model) {
        ItemEntity items = itemRepository.findById(id).get();
        System.out.println("items = " + items);
        model.addAttribute("items", items);
        return "item";
    }

    @DeleteMapping("/itemOne/{id}")
    public String itemDelete(@PathVariable("id") Long id) {
        itemRepository.deleteById(id);
        System.out.println(id);
        return "itemsList";
    }
}
