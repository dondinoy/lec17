package com.example.myblogproject.service;

import com.example.myblogproject.dto.UserRequestDto;
import com.example.myblogproject.dto.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    UserResponseDto register(UserRequestDto dto);
}
