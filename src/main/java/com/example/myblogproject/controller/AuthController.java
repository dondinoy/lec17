package com.example.myblogproject.controller;

import com.example.myblogproject.dto.UserRequestDto;
import com.example.myblogproject.dto.UserResponseDto;
import com.example.myblogproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> userDetails(Authentication authentication) {
        System.out.println(authentication);
            return ResponseEntity.ok(
                Map.of(
                        "username", authentication.getName(),
                        "authorities",authentication.getAuthorities()
                )
        );
    }
    @PostMapping("/register")

    public ResponseEntity<UserResponseDto> register(UserRequestDto dto, UriComponentsBuilder uriBuilder) {

        return ResponseEntity.created(uriBuilder.path("/login").build().toUri()).body(authService.register(dto));

    }
}