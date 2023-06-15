package com.aryan.blogging.bloggingapis.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.aryan.blogging.bloggingapis.exceptions.InvalidTokenHeaderException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component//So that it can be autowired
public class JwtTokenHelper {
    public static final long JWT_TOKEN_VALIDITY =2*24*60*60*60*1000;//in milliseconds

    Logger logger =LoggerFactory.getLogger(JwtTokenHelper.class); 
    private String secret = "jwtTokenKey";

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        //return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        Claims parsedClaims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        System.out.println("Parsed Claims: " + parsedClaims.getSubject());
        return parsedClaims;
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        logger.info(expiration.toString()+" Checking Token Expiration");
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        //return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        boolean isUsernameValid = username.equals(userDetails.getUsername());
        boolean isJwtTtokenExpired = isTokenExpired(token);
        System.out.println("Is token expired: " + isJwtTtokenExpired + " is username valid: " + isUsernameValid);
        if (isUsernameValid == false) {
            throw new InvalidTokenHeaderException("Username in the token is invalid");
        }
        if (isJwtTtokenExpired == true) {
            throw new InvalidTokenHeaderException("Token is expired!");
        }
        return (isUsernameValid && !isJwtTtokenExpired);
    }
    
}
