package com.aryan.blogging.bloggingapis.payload;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private int id;

    private String content;
    
    private Date date;
    
    

   private String firstname;
   private String lastname;

    
}
