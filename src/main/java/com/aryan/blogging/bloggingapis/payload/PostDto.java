package com.aryan.blogging.bloggingapis.payload;

import java.util.Date;

import com.aryan.blogging.bloggingapis.entities.Category;
import com.aryan.blogging.bloggingapis.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
public class PostDto {

 
    
    private String title;

    private String content;

    private String imageName="default.png";//We can give defult image name. You can remove it

    //We can take category id and user id here also or in URL

    private Date addedDate;

    //If we use Category in place of CategoryDTO, we will run into error or infinite recursion as both category and user have POST (Lists) which will lead to the recursion
    private CategoryDTO category;

    private UserDTO user;
    public PostDto()
    {
        System.out.println("Inside default PostDto constructor");
    }
}
