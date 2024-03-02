package com.example.myblogproject.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
        @NotNull
        @Size(min = 2)
        String username,
        @NotNull
        @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        String email,
        @NotNull
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*!@$%^&]).{8,32}$",
                message = "password must contain at least 1 lowercase letter,1 uppercase letter,1 digit and 1 special character"
        )
        String password) {
}