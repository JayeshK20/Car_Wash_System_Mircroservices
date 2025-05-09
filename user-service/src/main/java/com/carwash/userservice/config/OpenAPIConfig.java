package com.carwash.userservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                .title("User Service API")
                .description("API documentation for User Service | CarWash-Microservices")
                .version("1.0.0")
                        .contact(new Contact()
                                .name("Jayesh Kolambkar")
                                .email("jayeshkolambkar20@gmail.com")));
    }
}
