package com.aryan.blogging.bloggingapis.services;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

import com.aryan.blogging.bloggingapis.entities.Post;
import com.aryan.blogging.bloggingapis.payload.PostCreationDTO;
import com.aryan.blogging.bloggingapis.payload.PostDto;
import com.aryan.blogging.bloggingapis.payload.PostResponse;
import com.aryan.blogging.bloggingapis.payload.UserPostDTO;

public interface PostService {

    PostCreationDTO createPost(PostDto PostDto,Integer userId,Integer categoryId);

    PostCreationDTO updatePost(PostCreationDTO postDto, Integer postId,boolean iremoved);

    void deletePost(Integer postId);

    List<PostDto> getAllPost();
   

    PostDto getPostById(Integer postId);

    List<PostDto> getPostByCategory(Integer categoryId);


    List<UserPostDTO> getPostByUser(Integer userId);

    List<PostDto> searchPosts(String keyword);

    PostResponse getAllPosts(Integer pageSize,Integer pageNumber,String sortBy,String sortDir);
    
}
