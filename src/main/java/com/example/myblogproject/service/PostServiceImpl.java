package com.example.myblogproject.service;

import com.example.myblogproject.dto.PostCreateDto;
import com.example.myblogproject.dto.PostListDto;
import com.example.myblogproject.dto.PostResponseDto;
import com.example.myblogproject.entity.Post;
import com.example.myblogproject.error.PaginationException;
import com.example.myblogproject.error.ResourceNotFoundException;
import com.example.myblogproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    @Override
    public PostResponseDto createPost(PostCreateDto dto) {
        Post post=modelMapper.map(dto,Post.class);
        var saved=postRepository.save(post);
        return modelMapper.map(saved,PostResponseDto.class);
    }

    @Override
    public PostListDto getAllPosts(int pageNo, int pageSize, String sortDir, String... sortBy) {
        try{
            //Sort The Direction from string ('asc')
            Sort.Direction sort=Sort.Direction.fromString(sortDir);
            //Pagination Request of(pageNo,pageSize,sort,sortBy)
            Pageable pageable= PageRequest.of(pageNo,pageSize,sort,sortBy);
            //get the page result from the repository
            Page<Post>pr=postRepository.findAll(pageable);
            if (pageNo> pr.getTotalPages()){
                throw new PaginationException("The page Number You Requested:"+pageNo+" Exceeds totalPage"+ pr.getTotalPages());
            }
            //map the result to dto
            List<PostResponseDto>pageListDto=pr.getContent().stream().map(p->
                    modelMapper.map(p,PostResponseDto.class)).toList();
            //return a page of Post ListDto which contain all the posts
            return new PostListDto(
                    pr.getTotalElements(),
                    pr.getNumber(),
                    pr.getSize(),
                    pr.getTotalPages(),
                    pr.isFirst(),
                    pr.isLast(),
                    pageListDto
            );
        } catch (IllegalArgumentException e) {
            throw new PaginationException(e.getMessage());
        }

    }

    //    @Override
//    public PostListDto getAllPosts(){
//        List<PostResponseDto> postlist=postRepository.findAll().stream().map(v->
//                modelMapper.map(v,PostResponseDto.class)).toList();
//        return new PostListDto(postlist);
//    }
    @Override
    public PostResponseDto getPostById(long id){
        Post post=postRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Post","id",id));
        return modelMapper.map(post,PostResponseDto.class);
    }
    @Override
    public PostResponseDto updatePost(long id, PostCreateDto dto){
        Post post=getPostEntityOrThrow(id);
        props:
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        var saved=postRepository.save(post);
        return modelMapper.map(saved,PostResponseDto.class);
    }
    @Override
    public PostResponseDto deletePostById(long id) {
        Post post = getPostEntityOrThrow(id);
        postRepository.delete(post);
        return modelMapper.map(post, PostResponseDto.class);
    }

    @Override
    public Post getPostEntityOrThrow(long id) {
        return postRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Post","id",id));
    }

}
