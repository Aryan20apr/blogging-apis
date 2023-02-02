package com.aryan.blogging.bloggingapis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aryan.blogging.bloggingapis.entities.Comment;
import com.aryan.blogging.bloggingapis.payload.ApiResponse;
import com.aryan.blogging.bloggingapis.payload.CommentDto;
import com.aryan.blogging.bloggingapis.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<ApiResponse<CommentDto>> createComment(@RequestBody CommentDto comment,@PathVariable Integer postId,@PathVariable Integer userId)
    {
        CommentDto newComment=this.commentService.createComment(comment, postId,userId);
        ApiResponse<CommentDto> apiResponse=new ApiResponse<CommentDto>(newComment,"Commented Successfully",true);
        return new ResponseEntity<ApiResponse<CommentDto>>(apiResponse,HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Integer>> deleteComment(@PathVariable Integer commentId)
    {
       commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse<Integer>>(new ApiResponse<Integer>(commentId,"Cpmment Deleted",true),HttpStatus.OK);
    }
    
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<List<CommentDto>>> getAllCommentsOfPost(@PathVariable Integer postId)
    {
       List<CommentDto> comments=commentService.getAllComments(postId);
       ApiResponse<List<CommentDto>> apiResponse=new ApiResponse<List<CommentDto>>(comments,"Comments of post obtained",true);
       return new ResponseEntity<ApiResponse<List<CommentDto>>>(apiResponse,HttpStatus.OK);
    }

}
