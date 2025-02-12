package TenMWon.wiko.User.controller;

import TenMWon.wiko.User.domain.request.TokenRequest;
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

    @Value("${jwt.secret}")
    private String jwtSecret;

    @PostMapping("/google")
    public ResponseEntity<LoginResponse> googleLogin(@RequestBody TokenRequest tokenRequest) {
        String googleIdToken = tokenRequest.getToken();
        // 백엔드에서 Google ID 토큰을 검증 및 자동 회원가입 처리하는 메서드 호출
        String loginId = userService.processGoogleIdToken(googleIdToken);
        long expireTimeMs = 1000 * 60 * 60;
        String jwtToken = JwtTokenUtil.createToken(loginId, jwtSecret, expireTimeMs);
        LoginResponse response = new LoginResponse("로그인 성공", jwtToken);
        return ResponseEntity.ok(response);
    }
}
