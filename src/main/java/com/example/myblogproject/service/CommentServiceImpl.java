package com.example.myblogproject.service;

import com.example.myblogproject.dto.*;
import com.example.myblogproject.entity.Comment;
import com.example.myblogproject.entity.Post;
import com.example.myblogproject.entity.User;
import com.example.myblogproject.error.AuthenticationException;
import com.example.myblogproject.error.ResourceNotFoundException;
import com.example.myblogproject.error.UserAlreadyExistsException;
import com.example.myblogproject.repository.CommentRepository;
import com.example.myblogproject.repository.RoleRepository;
import com.example.myblogproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final ModelMapper modelMapper;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public CommentResponseDTO createComment(long postId, CommentRequestDTO dto, Authentication authentication) {
        Post post = postService.getPostEntityOrThrow(postId);
        var user = getUserEntityOrThrow(authentication);
        //map the dto to a Comment entity
        var comment = modelMapper.map(dto, Comment.class);
        //comment belongs to the Post
        comment.setPost(post);
        comment.setUser(user);
        var saved=commentRepository.save(comment);
        //save the comment
        var responseDto =  modelMapper.map(saved, CommentResponseDTO.class);
        responseDto.setUser(modelMapper.map(user,UserResponseDto.class));
        return responseDto;
    }

    private User getUserEntityOrThrow(Authentication authentication) {
        return userRepository.findUserByUsernameIgnoreCase(authentication.getName()).orElseThrow(
                () -> new AuthenticationException("User not found " + authentication.getName())
        );
    }

    private User checkPermissions(Authentication authentication, Comment comment) {
        var userFromDb = getUserEntityOrThrow(authentication);
        boolean isAdmin = userFromDb
                .getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase("ROLE_ADMIN"));
        if (!isAdmin && !Objects.equals(comment.getUser().getId(), userFromDb.getId())) {
            throw new AuthenticationException("User not found " + authentication.getName());
        }
        return userFromDb;

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
    public CommentResponseDTO updateCommentById(long id, CommentRequestDTO dto, Authentication authentication) {
        //check it exists
        Comment comment = getCommentEntityOrThrow(id);
        var user = checkPermissions(authentication, comment);
        var commentBeforeSave = modelMapper.map(dto, Comment.class);
        commentBeforeSave.setId(id);
        commentBeforeSave.setPost(comment.getPost());
        commentBeforeSave.setCreatedAt(comment.getCreatedAt());
        comment.setUser(user);
        var saved = commentRepository.save(commentBeforeSave);
        return modelMapper.map(saved, CommentResponseDTO.class);
    }
    @Override

    public CommentResponseDTO deleteCommentById(long id, Authentication authentication) {
        var comment = getCommentEntityOrThrow(id);
        checkPermissions(authentication, comment);
        commentRepository.delete(comment);
        return modelMapper.map(comment, CommentResponseDTO.class);
    }

    private Comment getCommentEntityOrThrow(long id) {
        return commentRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Comment","id",id));
    }
}
