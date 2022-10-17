package com.aryan.blogging.bloggingapis.payload;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
public class JwtAuthRequest {
    private String username;//email has been used as username
    private String password;
    
}
