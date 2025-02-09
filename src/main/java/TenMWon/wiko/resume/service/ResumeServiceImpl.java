package TenMWon.wiko.resume.service;

import TenMWon.wiko.resume.dto.in.CareerRequestDto;
import TenMWon.wiko.resume.dto.in.ResumeRequestDto;
import TenMWon.wiko.resume.entity.CareerType;
import TenMWon.wiko.resume.entity.Resume;
import TenMWon.wiko.resume.repository.CareerRepository;
import TenMWon.wiko.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService{

    private final ResumeRepository resumeRepository;
    private final CareerRepository careerRepository;


    @Override
    public void createResume(ResumeRequestDto resumeRequestDto) {
        Resume saveResume = resumeRepository.save(resumeRequestDto.toEntity());
        if(resumeRequestDto.getCareerType() == CareerType.경력 && resumeRequestDto.getCareerDetail() != null) {
            for(CareerRequestDto careerRequestDto : resumeRequestDto.getCareerDetail()) {
                careerRepository.save(careerRequestDto.toEntity(saveResume));
            }
        }
    }

//    @Override
//    public ResumeResponseDto readResume(Long userId) {
//        Resume resume = resumeRepository.findByUserId(userId)
//                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_RESUME));
//        return ResumeResponseDto.toDto(resume);
//    }

}
