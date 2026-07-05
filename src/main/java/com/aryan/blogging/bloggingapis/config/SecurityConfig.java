package com.aryan.blogging.bloggingapis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.aryan.blogging.bloggingapis.security.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomUserDetailService customUserDetailService;

    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http.csrf().disable().authorizeHttpRequests().anyRequest().authenticated().and().httpBasic();
	          
	        return http.build();
    
}
protected void configure(AuthenticationManagerBuilder auth) throws Exception{
	auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
}

@Bean
public PasswordEncoder passwordEncoder()
{
	return new BCryptPasswordEncoder();
}

}
