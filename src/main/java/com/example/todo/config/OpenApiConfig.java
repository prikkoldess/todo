package com.example.todo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

import io.swagger.v3.oas.annotations.info.Info;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.security.SecurityScheme;

import org.springframework.context.annotation.Configuration;

@Configuration

@OpenAPIDefinition(

                info = @Info(title = "Todo API", version = "1.0", description = "API для управления задачами"),

                security = @SecurityRequirement(name = "bearerAuth")

)

@SecurityScheme(

                name = "bearerAuth",

                type = SecuritySchemeType.HTTP,

                scheme = "bearer",

                bearerFormat = "JWT",

                description = "Введите JWT токен, полученный при логине"

)

public class OpenApiConfig {

}