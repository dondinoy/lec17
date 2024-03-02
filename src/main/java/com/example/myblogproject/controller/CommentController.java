package com.example.myblogproject.controller;

import com.example.myblogproject.dto.CommentListDto;
import com.example.myblogproject.dto.CommentRequestDTO;
import com.example.myblogproject.dto.CommentResponseDTO;
import com.example.myblogproject.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/posts/{id}/comments")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommentResponseDTO>createComment(
            Authentication authentication,
            @PathVariable(name = "id") Long postId,
            @Valid @RequestBody CommentRequestDTO dto,
            UriComponentsBuilder uriBilder) {
    var saved=commentService.createComment(postId,dto,authentication);
    var uri=uriBilder.path("/posts/{id}/comments").buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(uri).body(saved) ;
    }
    @GetMapping("/post/{id}/comments")
        public ResponseEntity<CommentListDto> getCommentsByPostId(@PathVariable(name = "id")long postId){
        return ResponseEntity.ok(commentService.findCommentsByPostId(postId));
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDTO> updateCommentById(
                @PathVariable long id, @RequestBody @Valid CommentRequestDTO dto,Authentication authentication){
        return ResponseEntity.ok(commentService.updateCommentById(id,dto,authentication));

    }
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDTO>deleteComment(@PathVariable long id,Authentication authentication){
        return ResponseEntity.ok(commentService.deleteCommentById(id,authentication));
    }
}
