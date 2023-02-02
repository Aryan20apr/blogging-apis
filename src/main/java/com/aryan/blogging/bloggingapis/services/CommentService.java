package com.aryan.blogging.bloggingapis.services;

import java.util.List;

import com.aryan.blogging.bloggingapis.payload.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto,Integer postId,Integer userID);

    void deleteComment(Integer commentId);
    
    List<CommentDto> getAllComments(Integer postId);
}
