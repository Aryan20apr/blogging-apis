package com.aryan.blogging.bloggingapis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.blogging.bloggingapis.payload.ApiResponse;
import com.aryan.blogging.bloggingapis.payload.PostDto;
import com.aryan.blogging.bloggingapis.payload.PostResponse;
import com.aryan.blogging.bloggingapis.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("user/{userId}/category/{categoryId}/posts") // Currently we are taking these in URL, but if there are
                                                              // many params, then we would have to use anothe pattern
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
            @PathVariable Integer categoryId) {

        PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
        List<PostDto> posts = this.postService.getPostByUser(userId);
        // System.out.println("List of Posts is "+posts.toArray());
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDto> posts = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }

    // @GetMapping("/posts")
    // public ResponseEntity<List<PostDto>> getAllPosts() {
    //     List<PostDto> posts = this.postService.getAllPost();
    //     return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    // }

    // @GetMapping("/posts")
    // public ResponseEntity<List<PostDto>> getAllPosts(
    //         @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
    //         @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
    //     List<PostDto> posts = this.postService.getAllPosts(pageNumber,pageSize);
    //     return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    // }
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        PostResponse posts = this.postService.getAllPosts(pageNumber,pageSize);
        return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getAllPostById(@PathVariable Integer postId) {
        PostDto post = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(post, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ApiResponse("Post is deleted successfully", true);
    }

    @PutMapping("/update/{postId}")
    public ApiResponse updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
        this.postService.updatePost(postDto, postId);
        return new ApiResponse("Post is updated successfully", true);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostDto>> findPosts(@RequestParam String keyword) {
        List<PostDto> posts = postService.searchPosts(keyword);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }

}
