package com.aryan.blogging.bloggingapis.security;

import java.io.IOException;
import java.util.Enumeration;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
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

    // This is called everytime the API request is hit
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Get Token
        String requestTokenHeader = request.getHeader("Authorization");// Key of the token which we will send in header
        Enumeration<String> headerNames = request.getHeaderNames();

		while(headerNames.hasMoreElements())
		{
			System.out.println(headerNames.nextElement());
		}
         /* Token is contained in
         * Bearer 2352523dgsg
         */
        System.out.println("Request Token Header " + requestTokenHeader);

        String username = null;
        String token = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
            token = requestTokenHeader.substring((7));// Actual token starts from the 7 th index
            try {
                username = jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get jwt token " + e.getMessage());
            } catch (ExpiredJwtException e) {
                System.out.println("Jwt Token Expired " + e.getMessage());
            } catch (MalformedJwtException e1) {
                System.out.println("Invalid Jwt " + e1.getMessage());
            }
        } else {
            System.out.println("Jwt token does not begin with Bearer");
        }
        //Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Username="+username);
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
            else
            {
                System.out.println("Invalid Jwt Token");
            }
        }
        else
        {
            System.out.println("Username is null or context is not null");
        }
        filterChain.doFilter(request, response);
    }

    

}
