package com.example.myblogproject.service;

import com.example.myblogproject.dto.CommentListDto;
import com.example.myblogproject.dto.CommentRequestDTO;
import com.example.myblogproject.dto.CommentResponseDTO;
import com.example.myblogproject.entity.Comment;
import com.example.myblogproject.entity.Post;
import com.example.myblogproject.error.ResourceNotFoundException;
import com.example.myblogproject.repository.CommentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final ModelMapper modelMapper;
    private final PostService postService;
    private final CommentRepository commentRepository;
    @Override
    public CommentResponseDTO createComment(long postId, CommentResponseDTO dto) {
        Post post= postService.getPostEntityOrThrow(postId);
        var comment=modelMapper.map(dto, Comment.class);
        comment.setPost(post);
        var saved=commentRepository.save(comment);
        return modelMapper.map(saved,CommentResponseDTO.class);
    }

    @Override
    public CommentListDto findCommentsByPostId(long id) {
        postService.getPostEntityOrThrow(id);
        commentRepository.findCommentsByPostId(id).stream().map(c->modelMapper.map(c,CommentResponseDTO.class)).toList();

        return new CommentListDto(
                commentRepository.findCommentsByPostId(id).stream().map(c->
                        modelMapper.map(c,CommentResponseDTO.class)).toList()
        );
    }

    @Override
    public CommentResponseDTO updateCommentById(long id, CommentRequestDTO dto) {
        var comment=commentRepository.findById(id).orElseThrow(
                 ResourceNotFoundException.newInstance("Comment","id",id)
        );
        var commentBeforeSave= modelMapper.map(dto,Comment.class);
        commentBeforeSave.setId(id);
        commentBeforeSave.setPost(comment.getPost());
        commentBeforeSave.setCreatedAt(comment.getCreatedAt());
        var saved=commentRepository.save(commentBeforeSave);

        return modelMapper.map(saved,CommentResponseDTO.class);
    }
    @Override
    public CommentResponseDTO deleteCommentById(long id) {
        var comment=getCommentEntityOrElseThrow(id);
        commentRepository.delete(comment);
        return modelMapper.map(comment,CommentResponseDTO.class);
    }

    private Comment getCommentEntityOrElseThrow(long id) {
        return commentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Comment","id",id));
    }
}
