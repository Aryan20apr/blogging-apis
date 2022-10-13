package com.aryan.blogging.bloggingapis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.aryan.blogging.bloggingapis.payload.PostDto;
import com.aryan.blogging.bloggingapis.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController 
{
    @Autowired
    private PostService postService;

    @PostMapping("user/{userId}/category/{categoryId}/posts")//Currently we are taking these in URL, but if there are many params, then we would have to use anothe pattern
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId)
    {

        PostDto createPost=this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
    }
    
}
