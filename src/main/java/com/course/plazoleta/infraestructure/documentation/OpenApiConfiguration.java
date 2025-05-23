package com.course.plazoleta.infraestructure.documentation;

import com.course.plazoleta.domain.utils.constants.OpenApiConstants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI customOpenApi(@Value("${appdescription}") String appDescription,
                                 @Value("${appversion}") String appVersion){


        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme(OpenApiConstants.TITLE_SWAGGER_BEARER_TOKEN)
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");


        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(OpenApiConstants.TITLE_SWAGGER_BEARER_TOKEN);


        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(OpenApiConstants.TITLE_SWAGGER_BEARER_TOKEN, securityScheme))
                .addSecurityItem(securityRequirement)
                .info(new Info()
                        .title("Hexagonal User API")
                        .version(appVersion)
                        .description(appDescription)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

}
