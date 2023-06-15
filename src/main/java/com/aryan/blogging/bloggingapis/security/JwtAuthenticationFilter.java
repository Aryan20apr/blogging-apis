package com.aryan.blogging.bloggingapis.security;

import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.aryan.blogging.bloggingapis.exceptions.InvalidTokenHeaderException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // to autowire it wherever necessary
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;
    
    Logger logger=LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    // This is called everytime the API request is hit
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
        // 1. Get Token
        String requestTokenHeader = request.getHeader("Authorization");// Key of the token which we will send in header
//        Enumeration<String> headerNames = request.getHeaderNames();
//
//		while(headerNames.hasMoreElements())
//		{
//			logger.info(headerNames.nextElement());
//		}
         /* Token is contained in
         * Bearer 2352523dgsg
         */
        logger.info("Request Token Header " + requestTokenHeader);

        String username = null;
        String token = null;
        logger.info("username " + username);
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
            token = requestTokenHeader.substring(7);// Actual token starts from the 7 th index
           
                username = jwtTokenHelper.getUsernameFromToken(token);
            
//        } else {
//            System.out.println("Jwt token does not begin with Bearer");
//        }
        //Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        logger.info("Username="+username);
        //once we get the token, now validate
        /**
         * SecurityContext-Interface defining the minimum security information associated with the current thread of execution.
         * 
         */
        if(username!=null&&SecurityContextHolder.getContext().getAuthentication()==null)
        {
            System.out.println("Inside if block");
            UserDetails user=userDetailsService.loadUserByUsername(username);
            if(this.jwtTokenHelper.validateToken(token, user))
            {
                // every thing is going right
                //Now Authenticate
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//https://www.baeldung.com/manually-set-user-authentication-spring-security
            }
            else {
                System.out.println(" jwt token validation returned false");
            }
        }
            else
            {
                logger.info("Invalid JWT Token");
                exceptionResolver.resolveException(request, response, null,
                        new InvalidTokenHeaderException(
                                "username is null: " + (username == null) + " authentication in context is null: "
                                        + (SecurityContextHolder.getContext().getAuthentication())));
                            }
        }
        else
        {
            logger.info("Username is null or context is not null");
        }
       // filterChain.doFilter(request, response);
        
        filterChain.doFilter(request, response);}
       
     catch (ExpiredJwtException e) {
         logger.info("Exception is 1"+e.toString());
         exceptionResolver.resolveException(request, response, null,
                new InvalidTokenHeaderException("Token is expired"));
    } catch (UnsupportedJwtException e) {
        logger.info("Exception is 2"+e.toString());
        exceptionResolver.resolveException(request, response, null,
                new InvalidTokenHeaderException(e.getMessage()));
    } catch (MalformedJwtException e) {
        logger.info("Exception is 3"+e.toString());
        exceptionResolver.resolveException(request, response, null,
                new InvalidTokenHeaderException(e.getMessage()));
    } catch (SignatureException e) {
        logger.info("Exception is 3"+e.toString());
        exceptionResolver.resolveException(request, response, null,
                new InvalidTokenHeaderException(e.getMessage()));
    } catch (IllegalArgumentException e) {
        logger.info("Exception is "+e.toString());
        exceptionResolver.resolveException(request, response, null,
                new InvalidTokenHeaderException(e.getMessage()));
    } catch (InvalidTokenHeaderException e) {
        logger.info("Exception is 4"+e.toString());
        exceptionResolver.resolveException(request, response, null, e);
    }
    }

    

}
