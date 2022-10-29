package com.aryan.blogging.bloggingapis.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    // All swagger configuration is done with the help of a docket class
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private ApiKey apiKeys()
    {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }
    private List<SecurityContext> securityContexts()
    {
        return Arrays.asList(SecurityContext.builder().securityReferences(securityreferences()).build());
    }
    private List<SecurityReference> securityreferences()
    {
        AuthorizationScope scopes=new AuthorizationScope("global","accessEverything");
            
        
        return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scopes}));
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(getInfo())
        .securityContexts(securityContexts())
        .securitySchemes(Arrays.asList(apiKeys()))
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
    }

    private ApiInfo getInfo() {
        return new ApiInfo("Blogging Application: APIs","This project is developed by Aryan Singh","1.0.1","Terms of Service",new Contact("Aryan Singh","https://www.google.com/","aryansingh20nov@gmail.com"),"License of APIs", "API license URL",Collections.EMPTY_LIST);
    }

    

}
