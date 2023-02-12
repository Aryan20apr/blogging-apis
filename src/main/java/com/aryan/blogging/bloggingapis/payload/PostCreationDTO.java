package com.aryan.blogging.bloggingapis.payload;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
// @NoArgsConstructor
public class PostCreationDTO {

    private Integer postId;

    private String title;

    private String content;

    private String imageName;// We can give defult image name. You can remove it

    // We can take category id and user id here also or in URL

    private String imageUrl;
    
    private String addedDate;

   
    public PostCreationDTO() {
        System.out.println("Inside default PostDto constructor");
    }
}
