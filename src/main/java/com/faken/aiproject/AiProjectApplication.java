package com.faken.aiproject;

import com.faken.aiproject.properties.MailProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MailProperties.class)
public class AiProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiProjectApplication.class, args);
    }

}
