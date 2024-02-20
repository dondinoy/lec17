package com.example.myblogproject.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDTO {
    private Long id;
    private String userName;
    private String email;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
