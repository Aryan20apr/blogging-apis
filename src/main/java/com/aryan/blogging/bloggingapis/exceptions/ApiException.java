package com.aryan.blogging.bloggingapis.exceptions;

public class ApiException extends RuntimeException{

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }
    
}
