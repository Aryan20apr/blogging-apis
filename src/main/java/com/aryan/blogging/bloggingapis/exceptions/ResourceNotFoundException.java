package com.aryan.blogging.bloggingapis.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    
    String resourceName;
    String fieldName;
    Integer fieldValue;
    String field;
    public ResourceNotFoundException(String resourceName, String fieldName, Integer fieldValue) {
        super(String.format("%s not found with %s : %s",resourceName, fieldName,fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public ResourceNotFoundException(String resourceName, String fieldName, String s) {
        super(String.format("%s not found with %s : %s",resourceName, fieldName,s));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.field = s;
    }
    
}
