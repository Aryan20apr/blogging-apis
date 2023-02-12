package com.aryan.blogging.bloggingapis.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.aryan.blogging.bloggingapis.payload.ApiResponse;
import com.aryan.blogging.bloggingapis.payload.UserDTO;

//@ControllerAdvice  Instead use
@RestControllerAdvice // With this we need not use @ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class) // ResourceNotFoundException class was created by me
    public ResponseEntity<ApiResponse<?>> resourceNotFoundExceptionHandler(ResourceNotFoundException e)

    {
        String message = e.getMessage();
        ApiResponse<?> apiResponse = new ApiResponse<>(null,message, false);
        return new ResponseEntity<ApiResponse<?>>(apiResponse, HttpStatus.OK);
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiResponse<UserDTO>> handleApiException(UserAlreadyExistException exception)

    {
        String message = exception.getMessage();
        Map<String,UserDTO> map= new HashMap<>();
        map.put("user", exception.user);
        ApiResponse<UserDTO> response=new ApiResponse<>(exception.user, message,false);
        return new ResponseEntity<ApiResponse<UserDTO>>(response, HttpStatus.OK);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> resp = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            resp.put(fieldName, message);
        });
        // System.out.println("\nError response is \n"+resp+"\n\n");

        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.OK);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<?>> handleApiException(ApiException exception)

    {
        String message = exception.getMessage();
        ApiResponse<?> apiResponse = new ApiResponse<>(null,message, false);
        return new ResponseEntity<ApiResponse<?>>(apiResponse, HttpStatus.OK);

    }
}
