package com.vuex.example.vuex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiDoc 
{
   @Bean
   public OpenAPI customOpenAPI()
   {
     return new OpenAPI().info(
         new Info()
                  .title("shortLinkBuuweb java edition")
                  .version("0.0.1")
                  .description("web app about shorting link ")  
                  .termsOfService("http://swagger.io/terms/")
        ).components(
            new Components()
                 .addSecuritySchemes("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
        );
        
   }
}
