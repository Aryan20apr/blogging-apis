package com.aryan.blogging.bloggingapis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("azure.myblob")
//Annotation for externalized configuration. 
//Add this to a class definition or a @Bean method in a @Configuration class if you 
//want to bind and validate some external Properties (e.g. from a .properties file).
@Component
public class AzureBlobProperties {
    private String connectionstring;
    private String container;
}