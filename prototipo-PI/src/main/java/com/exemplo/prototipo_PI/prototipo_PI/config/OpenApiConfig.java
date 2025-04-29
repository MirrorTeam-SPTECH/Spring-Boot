package com.exemplo.prototipo_PI.prototipo_PI.config;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Projeto Mirror",
                description = "O projeto Mirror visa espelhar a sua melhor versão.",
                contact = @Contact(
                        name = "Alisson, Isaque, Matheus, Kauan, Victoria, Ryan",
                        url = "https://github.com/MirrorTeam-SPTECH"
                ),

                license = @License(name = "UNLICENSED"),
                version = "1.0.0"
        )
)
@SecurityScheme(
        name = "Bearer", type = SecuritySchemeType.HTTP,scheme = "bearer", bearerFormat = "JWT"
)
public class OpenApiConfig {
}
