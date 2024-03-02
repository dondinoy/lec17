package com.example.myblogproject.service;

import com.example.myblogproject.dto.*;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

public interface CommentService {
//    CommentResponseDTO createComment(long postId,CommentResponseDTO dto, Authentication authentication);

    CommentResponseDTO createComment(long postId, CommentRequestDTO dto, Authentication authentication);

    CommentListDto findCommentsByPostId(long id);
    CommentResponseDTO updateCommentById(long id, CommentRequestDTO dto, Authentication authentication);
    CommentResponseDTO deleteCommentById(long id, Authentication authentication);

}
