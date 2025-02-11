package TenMWon.wiko.resume.service;

import TenMWon.wiko.User.domain.entity.User;
import TenMWon.wiko.User.repository.UserRepository;
import TenMWon.wiko.common.entity.BaseResponseStatus;
import TenMWon.wiko.common.exception.BaseException;
import TenMWon.wiko.resume.dto.in.CareerRequestDto;
import TenMWon.wiko.resume.dto.in.ResumeRequestDto;
import TenMWon.wiko.resume.dto.out.ResumeResponseDto;
import TenMWon.wiko.resume.entity.Career;
import TenMWon.wiko.resume.entity.CareerType;
import TenMWon.wiko.resume.entity.Resume;
import TenMWon.wiko.resume.repository.CareerRepository;
import TenMWon.wiko.resume.repository.ResumeRepository;
import TenMWon.wiko.security.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService{

    private final ResumeRepository resumeRepository;
    private final CareerRepository careerRepository;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.secret}")  // application.properties에서 secretKey 읽어오기
    private String secretKey;

    @Override
    public void createResume(String token, ResumeRequestDto resumeRequestDto) {
        String loginId = jwtTokenUtil.getLoginId(token.replace("Bearer ", ""), secretKey);
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));
        Resume saveResume = resumeRepository.save(resumeRequestDto.toEntity(user));
        if (resumeRequestDto.getCareerType() == CareerType.경력 && resumeRequestDto.getCareerDetail() != null) {
            CareerRequestDto careerRequestDto = resumeRequestDto.getCareerDetail();
            careerRepository.save(careerRequestDto.toEntity(saveResume));
        }
    }

    @Override
    public ResumeResponseDto readResume(String token) {
        String loginId = jwtTokenUtil.getLoginId(token.replace("Bearer ", ""), secretKey);

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));
        Resume resume = resumeRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_RESUME));
        CareerRequestDto careerDetail = null;
        if (resume.getCareerType() == CareerType.경력) {
            Career career = careerRepository.findByResumeResumeId(resume.getResumeId()).orElse(null);
            if (career != null) {
                careerDetail = CareerRequestDto.toDto(career);
            }
        }
        return ResumeResponseDto.toDto(resume, careerDetail);
    }
}
