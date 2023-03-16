package com.aryan.blogging.bloggingapis.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class InvalidTokenHeaderException extends RuntimeException{
    
    boolean success;
    String message;
    public InvalidTokenHeaderException(String message) {
        
        this.message=message;
        success=false;
    }

}
