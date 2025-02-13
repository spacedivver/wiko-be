package TenMWon.wiko.resume.service;

import TenMWon.wiko.User.domain.entity.User;
import TenMWon.wiko.User.repository.UserRepository;
import TenMWon.wiko.common.entity.BaseResponseStatus;
import TenMWon.wiko.common.exception.BaseException;
import TenMWon.wiko.recruit.dto.out.RecruitListResponseDto;
import TenMWon.wiko.resume.dto.in.CareerRequestDto;
import TenMWon.wiko.resume.dto.in.ResumeRequestDto;
import TenMWon.wiko.resume.dto.out.ResumeListResponseDto;
import TenMWon.wiko.resume.entity.Career;
import TenMWon.wiko.resume.entity.CareerType;
import TenMWon.wiko.resume.entity.Resume;
import TenMWon.wiko.resume.repository.CareerRepository;
import TenMWon.wiko.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final CareerRepository careerRepository;
    private final UserRepository userRepository;

    @Override
    public void createResume(ResumeRequestDto resumeRequestDto) {
        User user = userRepository.findByLoginId(resumeRequestDto.getLoginId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));
        Resume saveResume = resumeRepository.save(resumeRequestDto.toEntity(user));
        if (resumeRequestDto.getCareerType() == CareerType.경력 && resumeRequestDto.getCareerDetail() != null) {
            CareerRequestDto careerRequestDto = resumeRequestDto.getCareerDetail();
            careerRepository.save(careerRequestDto.toEntity(saveResume));
        }
    }

    @Override
    public Page<ResumeListResponseDto> readResumeList(int page, int size, String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<Resume> resumePage = resumeRepository.findByUser_UserId(user.getUserId(), pageable);
        if (resumePage.isEmpty()) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_RESUME);
        }
        return resumePage.map(resume -> {
            CareerRequestDto careerDetail = null;
            if (resume.getCareerType() == CareerType.경력) {
                Career career = careerRepository.findByResumeResumeId(resume.getResumeId()).orElse(null);
                if (career != null) {
                    careerDetail = CareerRequestDto.toDto(career);
                }
            }
            return ResumeListResponseDto.toDto(resume, careerDetail);
        });
    }

    @Transactional
    @Override
    public void deleteResume(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_RESUME));
        resumeRepository.deleteByResumeId(resumeId);
    }
}
