package com.aryan.blogging.bloggingapis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId)
    {
        List<PostDto> posts=this.postService.getPostByUser(userId);
        //System.out.println("List of Posts is "+posts.toArray());
        return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId)
    {
        List<PostDto> posts=this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
    }
    
}
