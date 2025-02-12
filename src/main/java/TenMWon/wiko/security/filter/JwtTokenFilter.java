package TenMWon.wiko.security.filter;

import TenMWon.wiko.User.domain.entity.User;
import TenMWon.wiko.User.service.UserService;
import TenMWon.wiko.security.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // JWT 토큰이 없거나 올바른 형식이 아니면 필터 체인 계속 진행
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // "Bearer " 뒤의 토큰 값 추출
        String token = authorizationHeader.split(" ")[1];

        // 토큰 만료 여부 검사
        if (JwtTokenUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 loginId 추출
        String loginId = JwtTokenUtil.getLoginId(token, secretKey);

        // loginId로 사용자 정보 조회
        User loginUser = userService.getLoginUserByLoginId(loginId);
        if (loginUser == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 사용자 정보를 기반으로 인증 객체 생성 후 SecurityContext에 저장
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser.getLoginId(), null,
                        List.of(new SimpleGrantedAuthority(loginUser.getRole().name())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
