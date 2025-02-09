package TenMWon.wiko._config;

import TenMWon.wiko.User.domain.entity.UserRole;
import TenMWon.wiko.User.service.UserService;
import TenMWon.wiko.security.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private static String secretKey = "my-secret-key-123123";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // URL에 대한 보안 규칙을 설정합니다.
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/jwt-login/info").authenticated()
                        .requestMatchers("/jwt-login/admin/**").hasAuthority(UserRole.ADMIN.name())
                        .anyRequest().permitAll()  // 기타 요청은 모두 허용 (필요에 따라 조정)
                )
                // 세션 정책을 stateless(상태 없음)으로 설정합니다.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // CSRF 보호를 비활성화합니다.
                .csrf(csrf -> csrf.disable())
                // HTTP Basic 인증을 비활성화합니다.
                .httpBasic(httpBasic -> httpBasic.disable())
                // JWT 토큰 필터를 UsernamePasswordAuthenticationFilter 앞에 추가합니다.
                .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
