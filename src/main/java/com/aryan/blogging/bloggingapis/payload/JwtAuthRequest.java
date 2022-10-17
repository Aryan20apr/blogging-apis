package com.aryan.blogging.bloggingapis.payload;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String username;// email has been used as username
    private String password;

}
