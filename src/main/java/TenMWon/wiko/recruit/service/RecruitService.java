package TenMWon.wiko.recruit.service;

import TenMWon.wiko.recruit.dto.in.RecruitRequestDto;
import TenMWon.wiko.recruit.dto.out.RecruitListResponseDto;
import TenMWon.wiko.recruit.dto.out.RecruitResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;

import java.util.List;

public interface RecruitService {

    void createRecruit(RecruitRequestDto recruitRequestDto);
    RecruitResponseDto readRecruitDetail(Long recruitId);
    Page<RecruitListResponseDto> readRecruitList(int page, int size);
    Page<RecruitListResponseDto> readFilterRecruitListWithLocal(List<String> industryTypeList, String startAddress, String endAddress,
                                                          Long minPay, Long maxPay, Pageable pageable);
    Page<RecruitListResponseDto> readLocalRecruitList(int page, int size);
    Page<RecruitListResponseDto> readFilterRecruitListWithSearch
            (String keyword, List<String> industryTypeList, String startAddress, String endAddress,
             Long minPay, Long maxPay, Pageable pageable);
    Page<RecruitListResponseDto> readTodayRecruit(int page, int size);
}
