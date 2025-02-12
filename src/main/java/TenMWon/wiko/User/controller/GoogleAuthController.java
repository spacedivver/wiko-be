package TenMWon.wiko.User.controller;


import TenMWon.wiko.User.domain.request.GoogleOAuthRequest;
import TenMWon.wiko.User.domain.response.LoginResponse;
import TenMWon.wiko.User.service.UserService;
import TenMWon.wiko.security.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class GoogleAuthController {

    private final UserService userService;

    // Next‑auth에서 사용한 JWT 비밀 키와 동일하게 설정 (application.properties에서 주입)
    @Value("${jwt.secret}")
    private String jwtSecret;

    @PostMapping("/google")
    public ResponseEntity<LoginResponse> googleLogin(@RequestBody GoogleOAuthRequest request) {
        // 프론트엔드에서 전달한 JSON에서 account 객체의 id_token 필드 추출
        String idToken = request.getAccount().getId_token();
        // UserService에서 Google ID 토큰을 검증하고 자동 회원가입(소셜 로그인)을 처리한 후 내부 로그인 아이디를 반환
        String loginId = userService.processGoogleIdToken(idToken);
        long expireTimeMs = 1000 * 60 * 60; // 예: 60분 유효
        // 내부 시스템용 JWT 토큰 발급 (예: "google_..." 형식의 loginId를 클레임으로 사용)
        String jwtToken = JwtTokenUtil.createToken(loginId, jwtSecret, expireTimeMs);
        LoginResponse response = new LoginResponse("로그인 성공", jwtToken);
        return ResponseEntity.ok(response);
    }
}
