package com.example.myblogproject.service;

import com.example.myblogproject.dto.CommentListDto;
import com.example.myblogproject.dto.CommentRequestDTO;
import com.example.myblogproject.dto.CommentResponseDTO;
import jakarta.validation.Valid;

public interface CommentService {
    CommentResponseDTO createComment(long postId,CommentResponseDTO dto);
    CommentListDto findCommentsByPostId(long id);
    CommentResponseDTO updateCommentById(long id,CommentRequestDTO dto);
    CommentResponseDTO deleteCommentById(long id);
}
