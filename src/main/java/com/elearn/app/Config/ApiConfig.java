package com.elearn.app.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.URL;
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Streaming app backend",
                description = "Created by Adarsh",
                version = "1.0V",
                contact = @Contact(
                        name = "streaming app",
                        email="av@gmail.com",
                        url="http://streaming.app.com"
                ),
                license = @License(
                        url="http://streaming.app.com",
                        identifier = "streaming application",
                        name = "Apache license 2.0"
                )
        ),
        security = @SecurityRequirement(
                name = "jwtScheme"
        )
)


@SecurityScheme(
        name = "jwtScheme",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)

public class ApiConfig {
   @Bean
   public ModelMapper modelMapper() {
      return new ModelMapper();
   }
}
