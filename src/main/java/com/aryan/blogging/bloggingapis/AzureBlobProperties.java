package com.aryan.blogging.bloggingapis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("azure.myblob")
@Component
public class AzureBlobProperties {
    private String connectionstring;
    private String container;
}