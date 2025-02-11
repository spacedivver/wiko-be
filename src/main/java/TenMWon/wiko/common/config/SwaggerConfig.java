package TenMWon.wiko.common.config;

import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@SecurityScheme(
        name = "bearerAuth", // SecurityScheme 이름
        type = SecuritySchemeType.HTTP, // HTTP 방식
        scheme = "bearer", // 인증 방식 (bearer)
        bearerFormat = "JWT" // JWT 형식 사용
)
public class SwaggerConfig {

    @Bean
    public OpenAPI OpenAPI() {
        return new OpenAPI()
                .components(new Components()) // 컴포넌트 추가
                .info(new Info()
                        .title("wiko API 서버")
                        .description("wiko API 명세서 입니다.")
                        .version("v0.0.1"));
    }
}
