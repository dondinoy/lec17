package com.example.myblogproject.service;

import com.example.myblogproject.dto.PostCreateDto;
import com.example.myblogproject.dto.PostListDto;
import com.example.myblogproject.dto.PostResponseDto;
import com.example.myblogproject.entity.Post;

public interface PostService {
    PostResponseDto createPost(PostCreateDto dto);
//    PostListDto getAllPosts();
    PostListDto getAllPosts(int pageNo, int pageSize, String sortDir, String... sortBy);
    PostResponseDto getPostById(long id);
    PostResponseDto updatePost(long id, PostCreateDto dto);

    PostResponseDto deletePostById(long id);
    Post getPostEntityOrThrow(long id);

}
