package TenMWon.wiko.recruit.service;

import TenMWon.wiko.recruit.dto.in.RecruitRequestDto;
import TenMWon.wiko.recruit.dto.out.RecruitListResponseDto;
import TenMWon.wiko.recruit.dto.out.RecruitResponseDto;
import org.springframework.data.domain.Page;

public interface RecruitService {

    void createRecruit(RecruitRequestDto recruitRequestDto);
    RecruitResponseDto readRecruitDetail(Long recruitId);
    Page<RecruitListResponseDto> readRecruitList(int page, int size);
}
