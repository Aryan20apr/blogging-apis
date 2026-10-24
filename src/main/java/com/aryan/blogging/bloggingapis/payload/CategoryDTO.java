package com.aryan.blogging.bloggingapis.payload;




import java.util.HashSet;
import java.util.Set;

import com.aryan.blogging.bloggingapis.entities.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    private Integer categoryId;

    /**
     * Issue: @NotBlank results in wrong message String
     */
     @NotBlank  
    @Size(min=4,max = 75,message = "Title must have between 4 and 75 characters")
    private String categoryTitle;

     @NotBlank
    @Size(min=4,max = 200,message = "Description must have at most 200 characters and must not be blank")
    private String categoryDescription;
    
    // private Set<User> users=new HashSet<>();
}
