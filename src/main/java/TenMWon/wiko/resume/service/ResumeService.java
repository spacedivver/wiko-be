package TenMWon.wiko.resume.service;

import TenMWon.wiko.recruit.dto.out.RecruitListResponseDto;
import TenMWon.wiko.resume.dto.in.ResumeRequestDto;
import TenMWon.wiko.resume.dto.out.ResumeListResponseDto;
import org.springframework.data.domain.Page;

public interface ResumeService {

    void createResume(ResumeRequestDto resumeRequestDto);
    Page<ResumeListResponseDto> readResumeList(int page, int size, String loginId);
//    void updateResumeImage(String loginId, ResumeImageRequestDto resumeImageRequestDto);
}
