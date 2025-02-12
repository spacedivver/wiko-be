package TenMWon.wiko.security.service;

import TenMWon.wiko.User.domain.entity.User;
import TenMWon.wiko.User.domain.entity.UserRole;
import TenMWon.wiko.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 기본 서비스로부터 사용자 정보 로드
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}", oAuth2User.getAttributes());

        // OAuth2 공급자 정보 (여기서는 "google" 등)
        String provider = userRequest.getClientRegistration().getRegistrationId();
        // 구글의 경우, 사용자 고유 식별자 "sub" 속성이 제공됨
        String providerId = oAuth2User.getAttribute("sub");
        // 우리 시스템에서는 로그인 아이디로 provider + "_" + providerId를 사용
        String loginId = provider + "_" + providerId;

        Optional<User> optionalUser = userRepository.findByLoginId(loginId);
        User user;
        if(optionalUser.isEmpty()) {
            // 신규 사용자라면 DB에 저장 (닉네임 등은 oAuth2User의 "name" 속성 사용)
            user = User.builder()
                    .loginId(loginId)
                    .name(oAuth2User.getAttribute("name"))
                    .provider(provider)
                    .providerId(providerId)
                    .role(UserRole.USER)
                    .build();
            userRepository.save(user);
        } else {
            // 기존 사용자라면 조회
            user = optionalUser.get();
        }

        // PrincipalDetails에 사용자 정보와 OAuth2 속성을 전달하여 반환
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}