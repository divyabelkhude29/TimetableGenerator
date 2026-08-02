package com.timetable.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI timetableAPI() {

        return new OpenAPI()

                .info(
                        new Info()

                                .title("Automatic Timetable Generation System API")

                                .description(
                                        "REST APIs for Timetable Management System"
                                )

                                .version("1.0")

                                .contact(
                                        new Contact()

                                                .name("Timetable Team")

                                                .email("support@timetable.com")
                                )

                                .license(
                                        new License()

                                                .name("CDAC Project")
                                )
                )

                .externalDocs(
                        new ExternalDocumentation()

                                .description("Project Documentation")
                );

    }

}