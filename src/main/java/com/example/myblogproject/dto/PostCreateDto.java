package com.example.myblogproject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateDto{
    @NotNull
    @Size(min = 2,max = 150)
    private String title;
    @NotNull
    @Size(min = 2)
    private String content;
}
