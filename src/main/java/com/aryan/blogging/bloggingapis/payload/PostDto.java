package com.aryan.blogging.bloggingapis.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
// @NoArgsConstructor
public class PostDto {

    private Integer postId;

    private String title;

    private String content;

    private String imageName = "default.png";// We can give defult image name. You can remove it

    // We can take category id and user id here also or in URL

    private Date addedDate;

    // If we use Category in place of CategoryDTO, we will run into error or
    // infinite recursion as both category and user have POST (Lists) which will
    // lead to the recursion
    private CategoryDTO category;

    private UserResponseDTO user;

    //private Set<CommentDto> comments = new HashSet<>();// With this we can get the comment of post with the post itself

    public PostDto() {
        System.out.println("Inside default PostDto constructor");
    }
}
