package com.aryan.blogging.bloggingapis.services;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

import com.aryan.blogging.bloggingapis.entities.Post;
import com.aryan.blogging.bloggingapis.payload.PostDto;

public interface PostService {

    PostDto createPost(PostDto PostDto,Integer userId,Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    List<PostDto> getAllPost();

    PostDto getPostById(Integer postId);

    List<PostDto> getPostByCategory(Integer categoryId);


    List<PostDto> getPostByUser(Integer userId);

    List<PostDto> searchPosts(String keyword);

    List<PostDto> getAllPosts(Integer pageSize,Integer pageNumber);
    
}
