package com.aryan.blogging.bloggingapis.security;

import java.io.IOException;



import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

    @Override
    //This method is called when unaothorized user tries to call an api
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
       
       
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Access Denied");
        
    }
    
}
