package TenMWon.wiko.User.service;

import TenMWon.wiko.User.domain.entity.User;
import TenMWon.wiko.User.domain.entity.UserRole;
import TenMWon.wiko.User.domain.request.JoinRequest;
import TenMWon.wiko.User.domain.request.LoginRequest;
import TenMWon.wiko.User.domain.request.UserProfileUpdateRequest;
import TenMWon.wiko.User.domain.response.UserProfileResponse;
import TenMWon.wiko.User.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // Spring Security를 사용한 로그인 구현 시 사용
     private final BCryptPasswordEncoder encoder;

    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public void join(JoinRequest req) {
        userRepository.save(req.toEntity(encoder.encode(req.getPassword())));
    }

    public User login(LoginRequest req) {
        Optional<User> optionalUser = userRepository.findByLoginId(req.getLoginId());

        if(optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            return null;
        }

        return user;
    }

    public User getLoginUserByLoginId(String loginId) {
        if(loginId == null) return null;

        Optional<User> optionalUser = userRepository.findByLoginId(loginId);
        if(optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }

    public void updateUserProfile(String loginId, UserProfileUpdateRequest req) {
        // 로그인 아이디로 사용자 조회
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with loginId: " + loginId));

        // 프로필 필드 업데이트
        user.setAge(req.getAge());
        user.setEducation(req.getEducation());
        user.setExperience(req.getExperience());
        user.setKoreanProficiency(req.getKoreanProficiency());
        user.setRegion(req.getRegion());
        user.setVisaDescription(req.getVisaDescription());

        // 변경된 내용은 JPA가 트랜잭션 커밋 시 자동으로 업데이트합니다.
        userRepository.save(user);
    }

    public UserProfileResponse getUserProfile(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with loginId: " + loginId));

        // 날짜 형식을 원하는 형식으로 변환 (예시: "yyyy-MM-dd")
        String formattedBirth = null;
        if (user.getBirth() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            formattedBirth = sdf.format(user.getBirth());
        }

        // UserProfileDTO에 필요한 정보를 채워서 반환
        return new UserProfileResponse(
                user.getLoginId(),
                user.getName(),
                formattedBirth,
                user.getPhoneNumber(),
                user.getEmail(),
                user.getAddress(),
                user.getVisaType(),
                user.getAge(),
                user.getEducation(),
                user.getExperience(),
                user.getKoreanProficiency(),
                user.getRegion(),
                user.getVisaDescription()
        );
    }

    // UserService 클래스 내부
    public String processGoogleIdToken(String idToken) {
        try {
            // 1. 토큰 파싱: 전달받은 idToken을 SignedJWT 객체로 파싱합니다.
            SignedJWT signedJWT = SignedJWT.parse(idToken);

            // 2. 토큰 헤더에서 'kid' 값을 추출합니다.
            String kid = signedJWT.getHeader().getKeyID();
            if (kid == null) {
                throw new Exception("토큰에 'kid' 값이 없습니다.");
            }

            // 3. Google의 JWKS 엔드포인트에서 공개 키 세트를 로드합니다.
            URL jwksUrl = new URL("https://www.googleapis.com/oauth2/v3/certs");
            JWKSet jwkSet = JWKSet.load(jwksUrl);

            // 4. 토큰의 'kid'와 일치하는 공개 키를 찾습니다.
            JWK jwk = jwkSet.getKeyByKeyId(kid);
            if (jwk == null) {
                throw new Exception("토큰의 'kid'에 해당하는 공개 키를 찾을 수 없습니다: " + kid);
            }
            RSAPublicKey publicKey = jwk.toRSAKey().toRSAPublicKey();

            // 5. 토큰 서명 검증: 공개 키를 사용하여 서명을 검증합니다.
            JWSVerifier verifier = new RSASSAVerifier(publicKey);
            if (!signedJWT.verify(verifier)) {
                throw new Exception("토큰 서명 검증에 실패했습니다.");
            }

            // 6. 토큰 디코딩: JWTClaimsSet을 통해 클레임을 추출합니다.
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            String provider = "google";
            // 'sub' 클레임은 Google에서 부여한 고유 사용자 ID입니다.
            String providerId = claims.getStringClaim("sub");
            // 'name' 클레임은 사용자의 이름입니다.
            String extractedName = claims.getStringClaim("name");

            // 내부 시스템에서 사용할 로그인 아이디 형식: "google_{sub}"
            String loginId = provider + "_" + providerId;

            // 7. 데이터베이스에 해당 사용자가 있는지 확인 후, 없으면 신규 회원가입 처리
            Optional<User> optionalUser = userRepository.findByLoginId(loginId);
            if (optionalUser.isEmpty()) {
                User newUser = User.builder()
                        .loginId(loginId)
                        .name(extractedName)
                        .provider(provider)
                        .providerId(providerId)
                        .role(UserRole.USER)
                        .build();
                userRepository.save(newUser);
            }
            return loginId;

        } catch (Exception e) {
            // 실제 서비스에서는 적절한 예외 처리를 수행합니다.
            throw new RuntimeException("Google ID 토큰 처리 실패", e);
        }
    }


}
