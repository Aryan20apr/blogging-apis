package com.aryan.blogging.bloggingapis.payload;

import lombok.Data;

@Data
public class JwtAuthResponse {
//Contains variable which is to be sent with the data. Here we just have to send the token.
    private String token;
    private String message;
    private boolean success;

    
}
