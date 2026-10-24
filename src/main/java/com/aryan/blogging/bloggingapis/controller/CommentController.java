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
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/co")
    public ResponseEntity<ApiResponse<CommentDto>> createComment(@RequestBody CommentDto comment,@RequestParam Integer postId,@RequestParam Integer userId)
    {
        CommentDto newComment=this.commentService.createComment(comment, postId,userId);
        ApiResponse<CommentDto> apiResponse=new ApiResponse<CommentDto>(newComment,"Commented Successfully",true);
        return new ResponseEntity<ApiResponse<CommentDto>>(apiResponse,HttpStatus.CREATED);
    }

    @DeleteMapping("/co")
    public ResponseEntity<ApiResponse<Integer>> deleteComment(@RequestParam Integer id)
    {
       commentService.deleteComment(id);
        return new ResponseEntity<ApiResponse<Integer>>(new ApiResponse<Integer>(id,"Cpmment Deleted",true),HttpStatus.OK);
    }
    
    @GetMapping("/co")
    public ResponseEntity<ApiResponse<List<CommentDto>>> getAllCommentsOfPost(@RequestParam Integer postId)
    {
       List<CommentDto> comments=commentService.getAllComments(postId);
       ApiResponse<List<CommentDto>> apiResponse=new ApiResponse<List<CommentDto>>(comments,"Comments of post obtained",true);
       return new ResponseEntity<ApiResponse<List<CommentDto>>>(apiResponse,HttpStatus.OK);
    }

}
