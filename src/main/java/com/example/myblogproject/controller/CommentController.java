package com.example.myblogproject.controller;

import com.example.myblogproject.dto.CommentListDto;
import com.example.myblogproject.dto.CommentRequestDTO;
import com.example.myblogproject.dto.CommentResponseDTO;
import com.example.myblogproject.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<CommentResponseDTO>createComment(
            @PathVariable(name = "id") Long postId,
            @Valid @RequestBody CommentResponseDTO dto,
            UriComponentsBuilder uriBilder) {
    var saved=commentService.createComment(postId,dto);
    var uri=uriBilder.path("/posts/{id}/comments").buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(uri).body(saved) ;
    }
    @GetMapping("/post/{id}/comments")
        public ResponseEntity<CommentListDto> getCommentsByPostId(@PathVariable(name = "id")long postId){
        return ResponseEntity.ok(commentService.findCommentsByPostId(postId));
    }

    @PutMapping("/comments/{id}")
        public ResponseEntity<CommentResponseDTO> updateCommentById(
                @PathVariable long id, @RequestBody @Valid CommentRequestDTO dto){
        return ResponseEntity.ok(commentService.updateCommentById(id,dto));

    }
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDTO>deleteComment(@PathVariable long id){
        return ResponseEntity.ok(commentService.deleteCommentById(id));
    }
}
