package com.example.myblogproject.controller;

import com.example.myblogproject.dto.*;
import com.example.myblogproject.service.AuthService;
import com.example.myblogproject.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final AuthService authService;






    @GetMapping
    public ResponseEntity<PostListDto> getAllPosts(
        @RequestParam(value = "pageNo",required = false,defaultValue = "0")int pageNo,
        @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
        @RequestParam(value = "pageDir",required = false,defaultValue = "desc")String pageDir,
        @RequestParam(value = "sortBy",required = false,defaultValue = "id")String...sortBy
        ){
        System.out.println(Arrays.toString(sortBy));
            return ResponseEntity.ok(postService.getAllPosts(pageNo,pageSize, pageDir,sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto>getPostById(@PathVariable long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostResponseDto> createPost(
            @RequestBody @Valid PostCreateDto dto,
            UriComponentsBuilder uriBuilder
    ) {
        var res = postService.createPost(dto);
        var uri = uriBuilder.path("/api/v1/posts/{id}").buildAndExpand(res.getId()).toUri();
        return ResponseEntity.created(uri).body(res);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<PostResponseDto>updatePost(@PathVariable long id, @RequestBody @Valid PostCreateDto dto ){
        return ResponseEntity.ok(postService.updatePost(id, dto));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<PostResponseDto>deletePost(@PathVariable long id){
        return ResponseEntity.ok(postService.deletePostById(id));
    }
}
