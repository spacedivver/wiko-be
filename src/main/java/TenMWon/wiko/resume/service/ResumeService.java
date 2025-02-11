package TenMWon.wiko.resume.service;

import TenMWon.wiko.resume.dto.in.ResumeRequestDto;
import TenMWon.wiko.resume.dto.out.ResumeResponseDto;

public interface ResumeService {

//    void createResume(Long userId, ResumeRequestDto resumeRequestDto);
    ResumeResponseDto readResume(Long userId);
}
