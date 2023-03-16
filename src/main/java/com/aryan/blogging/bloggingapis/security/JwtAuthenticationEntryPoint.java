package com.aryan.blogging.bloggingapis.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
    
    Logger logger=LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    @Override
    //This method is called when unaothorized user tries to call an api
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
       
       logger.info("Inside commence method");
               // response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Access Denied");
      resolver.resolveException(request, response, null, authException);
    
}
}
