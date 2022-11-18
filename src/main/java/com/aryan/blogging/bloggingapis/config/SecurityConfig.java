package com.aryan.blogging.bloggingapis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.aryan.blogging.bloggingapis.security.CustomUserDetailService;
import com.aryan.blogging.bloggingapis.security.JwtAuthenticationEntryPoint;
import com.aryan.blogging.bloggingapis.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity

@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    // @Autowired
    // private CustomUserDetailService customUserDetailService;

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // http.csrf().disable().authorizeHttpRequests().anyRequest().authenticated().and().httpBasic();

    // return http.build();

    // }
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    // auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
    // }

    // @Bean
    // public PasswordEncoder passwordEncoder()
    // {
    // return new BCryptPasswordEncoder();
    // }
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public static final String[] PUBLIC_URLS = { "/api/auth/**",
            "/v3/api-docs", "/v2/api-docs",
            "/swagger-resources/**", "/swagger-ui/**",
            "/webjars/**"

    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers(PUBLIC_URLS)
                .permitAll()
                 .antMatchers(HttpMethod.GET)
                 .permitAll()
                .anyRequest()
                .authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)// If at any time, any exception is generated
                                                                           // due to unauthourization, the commence
                                                                           // method will be executed inside the
                                                                           // JwtAuthenticationEntryPoint
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Here we have set the jwtAuthenticationEntryPoint and session management
        // policies.
        // Session management policies are stateless as jwt authentication works on
        // statless mechanism

        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(daoAuthenticationProvider());
        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();

        return defaultSecurityFilterChain;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        // Required to authenticate the password
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // @Bean
    // public FilterRegistrationBean coresFilter() {
    // UrlBasedCorsConfigurationSource source = new
    // UrlBasedCorsConfigurationSource();

    // CorsConfiguration corsConfiguration = new CorsConfiguration();
    // corsConfiguration.setAllowCredentials(true);
    // corsConfiguration.addAllowedOriginPattern("*");
    // corsConfiguration.addAllowedHeader("Authorization");
    // corsConfiguration.addAllowedHeader("Content-Type");
    // corsConfiguration.addAllowedHeader("Accept");
    // corsConfiguration.addAllowedMethod("POST");
    // corsConfiguration.addAllowedMethod("GET");
    // corsConfiguration.addAllowedMethod("DELETE");
    // corsConfiguration.addAllowedMethod("PUT");
    // corsConfiguration.addAllowedMethod("OPTIONS");
    // corsConfiguration.setMaxAge(3600L);

    // source.registerCorsConfiguration("/**", corsConfiguration);

    // FilterRegistrationBean bean = new FilterRegistrationBean(new
    // CorsFilter(source));

    // bean.setOrder(-110);

    // return bean;
    // }

}