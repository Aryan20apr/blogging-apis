package com.aryan.blogging.bloggingapis.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aryan.blogging.bloggingapis.payload.ApiResponse;
import com.aryan.blogging.bloggingapis.payload.CategoryDTO;
import com.aryan.blogging.bloggingapis.payload.PostDto;
import com.aryan.blogging.bloggingapis.payload.PostResponse;
import com.aryan.blogging.bloggingapis.services.FileService;
import com.aryan.blogging.bloggingapis.services.PostService;
import com.aryan.blogging.bloggingapis.utils.Constants;



@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("user/{userId}/category/{categoryId}/posts") // Currently we are taking these in URL, but if there are
                                                              // many params, then we would have to use anothe pattern
    public ResponseEntity<ApiResponse<PostDto>> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
            @PathVariable Integer categoryId) {

        PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
        ApiResponse<PostDto> apiResponse=new ApiResponse<PostDto>(createPost,"Post created successfully",true);
        return new ResponseEntity<ApiResponse<PostDto>>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<ApiResponse<List<PostDto>>> getPostByUser(@PathVariable Integer userId) {
        List<PostDto> posts = this.postService.getPostByUser(userId);
        
        ApiResponse<List<PostDto>> apiResponse=new ApiResponse<>(posts,"Posts of the user are",true);
        return new ResponseEntity<ApiResponse<List<PostDto>>>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<ApiResponse<List<PostDto>>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDto> posts = this.postService.getPostByCategory(categoryId);
        ApiResponse<List<PostDto>> apiResponse=new ApiResponse<>(posts,"Posts in the category",true);
        return new ResponseEntity<ApiResponse<List<PostDto>>>(apiResponse, HttpStatus.OK);
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
    // @GetMapping("/posts")
    // public ResponseEntity<PostResponse> getAllPosts(
    //         @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
    //         @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
    //     PostResponse posts = this.postService.getAllPosts(pageNumber,pageSize);
    //     return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
    // }
    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<PostResponse>> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = Constants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Constants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = Constants.SORT_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = Constants.SORT_DIR, required = false ) String sortDir ) {
        PostResponse posts = this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
        ApiResponse<PostResponse> apiResponse=new ApiResponse<PostResponse>(posts,"Posts obtained",true);
        return new ResponseEntity<ApiResponse<PostResponse>>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<PostDto>> getAllPostById(@PathVariable Integer postId) {
        PostDto post = this.postService.getPostById(postId);
        ApiResponse<PostDto> apiResponse=new ApiResponse<PostDto>(post,"Post obtained ",true);
        return new ResponseEntity<ApiResponse<PostDto>>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<ApiResponse<Integer>> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        ApiResponse<Integer> apiResponse=new ApiResponse<Integer>(postId,"Post obtained ",true);
        return new ResponseEntity<ApiResponse<Integer>>(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<ApiResponse<Integer>> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
        this.postService.updatePost(postDto, postId);
        ApiResponse<Integer> apiResponse=new ApiResponse<Integer>(postId,"Post obtained ",true);
        return new ResponseEntity<ApiResponse<Integer>>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostDto>> findPosts(@RequestParam String keyword) {
        List<PostDto> posts = postService.searchPosts(keyword);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }
    // post image upload


    @PostMapping("/post/image/upload/{postId}")//PostId to identify the post whose image is uploaded
    private ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable Integer postId) throws IOException
    {
        PostDto postDto=postService.getPostById(postId);
        String fileName=fileService.uploadImage(path, file); 
        
        postDto.setImageName(fileName);
        this.postService.updatePost(postDto, postId);
        
        return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
    }
    //Method to serve files
    @GetMapping(path="/post/image/download/{imageName}",produces=MediaType.IMAGE_PNG_VALUE)
     public void downloadFile(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException
    {
     InputStream resource = this.fileService.getResource(path,imageName);
     response.setContentType(MediaType.IMAGE_PNG_VALUE);//Specify the type of content to be sent in response
     StreamUtils.copy(resource, response.getOutputStream());//Put the data obtained from InputStream to the output Stream
    }
 

}
