package TenMWon.wiko._config;

import TenMWon.wiko.User.domain.entity.UserRole;
import TenMWon.wiko.security.handler.MyAccessDeniedHandler;
import TenMWon.wiko.security.handler.MyAuthenticationEntryPoint;
import TenMWon.wiko.security.service.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;

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
                // 폼 로그인 설정
                .formLogin(form -> form
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                        .loginPage("/jwt-login/login")       // 커스텀 로그인 페이지 URL
                        .defaultSuccessUrl("/jwt-login")       // 로그인 성공 시 이동할 URL
                        .failureUrl("/jwt-login/login")        // 로그인 실패 시 이동할 URL
                )
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/jwt-login/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                // OAuth2 로그인 설정 (예: Google 로그인)
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/jwt-login/login")
                        .defaultSuccessUrl("/jwt-login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(principalOauth2UserService)
                        )
                )
                // 세션 관리: JWT 기반 인증을 사용하는 경우 STATELESS (JWT는 서버에 상태를 유지하지 않음)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 예외 처리 설정 (인증 실패, 권한 부족 시)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                        .accessDeniedHandler(new MyAccessDeniedHandler())
                );

        return http.build();
    }
}
