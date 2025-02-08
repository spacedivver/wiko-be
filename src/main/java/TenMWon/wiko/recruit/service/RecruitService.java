package TenMWon.wiko.recruit.service;

import TenMWon.wiko.recruit.dto.in.RecruitRequestDto;
import TenMWon.wiko.recruit.dto.out.RecruitListResponseDto;
import TenMWon.wiko.recruit.dto.out.RecruitResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecruitService {

    void createRecruit(RecruitRequestDto recruitRequestDto);
    RecruitResponseDto readRecruitDetail(Long recruitId);
    Page<RecruitListResponseDto> readRecruitList(int page, int size);
    Page<RecruitListResponseDto> readFilterRecruitList
            (String industryType, String startAddress, String endAddress,
             Long minSalary, Long maxSalary, Pageable pageable);
}
