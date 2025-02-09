package TenMWon.wiko.resume.service;

import TenMWon.wiko.resume.dto.in.ResumeRequestDto;
import TenMWon.wiko.resume.entity.Resume;
import TenMWon.wiko.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService{

    private final ResumeRepository resumeRepository;


    @Override
    public void createResume(ResumeRequestDto resumeRequestDto) {
        Resume saveResume = resumeRepository.save(resumeRequestDto.toEntity());
    }
}
