package com.aryan.blogging.bloggingapis.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.aryan.blogging.bloggingapis.payload.ApiResponse;

//@ControllerAdvice  Instead use
@RestControllerAdvice //With this we need not use @ResponseBody
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)//ResourceNotFoundException class was created by me
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e)
    
    {
        String message=e.getMessage();
        ApiResponse apiResponse=new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
