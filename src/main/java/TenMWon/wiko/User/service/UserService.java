package TenMWon.wiko.User.service;

import TenMWon.wiko.User.domain.entity.User;
import TenMWon.wiko.User.domain.request.JoinRequest;
import TenMWon.wiko.User.domain.request.LoginRequest;
import TenMWon.wiko.User.domain.request.UserProfileUpdateRequest;
import TenMWon.wiko.User.domain.response.UserProfileResponse;
import TenMWon.wiko.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
