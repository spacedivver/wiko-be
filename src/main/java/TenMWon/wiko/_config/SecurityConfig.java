package TenMWon.wiko._config;

import TenMWon.wiko.User.domain.entity.UserRole;
import TenMWon.wiko.User.service.UserService;
import TenMWon.wiko.security.filter.JwtTokenFilter;
import TenMWon.wiko.security.handler.MyAccessDeniedHandler;
import TenMWon.wiko.security.handler.MyAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public SecurityFilterChain securityFilterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화 (API 중심 애플리케이션의 경우)
                .csrf(csrf -> csrf.disable())
                // URL 별 접근 제어
                .authorizeHttpRequests(authorize -> authorize
                        // 인증이 필요한 URL
                        .requestMatchers("/jwt-login/profile").authenticated()
                        // ADMIN 권한이 필요한 URL
                        .requestMatchers("/jwt-login/admin/**").hasAuthority(UserRole.ADMIN.name())
                        // 그 외의 모든 요청은 허용
                        .anyRequest().permitAll()
                )
                // 세션 관리: JWT 기반 인증을 사용하는 경우 STATELESS (JWT는 서버에 상태를 유지하지 않음)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 예외 처리 설정 (인증 실패, 권한 부족 시)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                        .accessDeniedHandler(new MyAccessDeniedHandler())
                );

        // JWT 토큰 필터 추가: 모든 요청 전에 JWT 토큰을 검증합니다.
        http.addFilterBefore(new JwtTokenFilter(userService, jwtSecret), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
