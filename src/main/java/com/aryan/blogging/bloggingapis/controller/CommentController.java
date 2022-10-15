package com.aryan.blogging.bloggingapis.controller;

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
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment,@PathVariable Integer postId,@PathVariable Integer userId)
    {
        CommentDto newComment=this.commentService.createComment(comment, postId,userId);
        return new ResponseEntity<CommentDto>(newComment,HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId)
    {
       commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Cpmment Deleted",true),HttpStatus.OK);
    }

}
