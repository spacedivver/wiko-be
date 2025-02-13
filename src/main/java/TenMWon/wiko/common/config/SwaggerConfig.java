package TenMWon.wiko.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String BEARER_TOKEN_PREFIX = "Bearer";

    @Bean
    public OpenAPI openAPI() {
        // JWT Security 설정
        String securityJwtName = "bearerAuth";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);

        Components components = new Components().addSecuritySchemes(securityJwtName,
                new SecurityScheme().name(securityJwtName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(BEARER_TOKEN_PREFIX)
                        .bearerFormat("JWT"));

        return new OpenAPI().addSecurityItem(securityRequirement)
                .components(components)
                .addServersItem(new Server().url(""))
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info().title("Wiko API 서버")
                .description("Wiko API 문서입니다.")
                .version("v0.0.1");
    }
}