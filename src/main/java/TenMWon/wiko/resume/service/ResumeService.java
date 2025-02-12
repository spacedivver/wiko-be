package TenMWon.wiko.resume.service;

import TenMWon.wiko.resume.dto.in.ResumeRequestDto;
import TenMWon.wiko.resume.dto.out.ResumeResponseDto;
import org.springframework.security.core.Authentication;

public interface ResumeService {

    void createResume(ResumeRequestDto resumeRequestDto);
    ResumeResponseDto readResume(String loginId);
}
