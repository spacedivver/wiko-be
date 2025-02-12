package TenMWon.wiko.recruit.service;


import TenMWon.wiko.common.entity.BaseResponseStatus;
import TenMWon.wiko.common.exception.BaseException;
import TenMWon.wiko.recruit.dto.in.RecruitRequestDto;
import TenMWon.wiko.recruit.dto.out.RecruitListResponseDto;
import TenMWon.wiko.recruit.dto.out.RecruitResponseDto;
import TenMWon.wiko.recruit.entity.Recruit;
import TenMWon.wiko.recruit.repository.RecruitRepository;
import TenMWon.wiko.recruit.repository.RecruitRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitServiceImpl implements RecruitService {

    private final RecruitRepository recruitRepository;
    private final RecruitRepositoryCustom recruitRepositoryCustom;

    @Override
    public void createRecruit(RecruitRequestDto recruitRequestDto) {
        Recruit saveRecruit = recruitRepository.save(recruitRequestDto.toEntity());
    }

    @Override
    public RecruitResponseDto readRecruitDetail(Long recruitId) {
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_RECRUIT));
        return RecruitResponseDto.toDto(recruit);
    }

    @Override
    public Page<RecruitListResponseDto> readRecruitList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<Recruit> recruitPage = recruitRepository.findAll(pageable);
        if (recruitPage.isEmpty()) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_RECRUIT);
        }
        return recruitPage.map(RecruitListResponseDto::toDto);
    }

    @Override
    public Page<RecruitListResponseDto> readFilterRecruitListWithSearch(String keyword, List<String> industryTypeList, String startAddress, String endAddress, Long minPay, Long maxPay, Pageable pageable) {
        Page<Recruit> recruitPage = recruitRepositoryCustom.findRecruitWithFilters(
                industryTypeList, startAddress, endAddress, minPay, maxPay, keyword, pageable);
        if (recruitPage.isEmpty()) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_RECRUIT);
        }
        return recruitPage.map(RecruitListResponseDto::toDto);
    }

//    @Override
//    public Page<RecruitListResponseDto> readRecruitSearch(String keyword, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
//        Page<Recruit> recruitSearch = recruitRepository.findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(keyword, keyword, pageable);
//        if (recruitSearch.isEmpty()) {
//            throw new BaseException(BaseResponseStatus.NO_EXIST_RECRUIT);
//        }
//        return recruitSearch.map(RecruitListResponseDto::toDto);
//    }
}