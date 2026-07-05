package com.aryan.blogging.bloggingapis.services;

import com.aryan.blogging.bloggingapis.payload.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto,Integer postId,Integer userID);

    void deleteComment(Integer commentId);
}
