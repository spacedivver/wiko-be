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

@Service
@RequiredArgsConstructor
public class RecruitServiceImpl implements RecruitService{

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
        return recruitRepository.findAll(pageable)
                .map(RecruitListResponseDto::toDto);
    }

    @Override
    public Page<RecruitListResponseDto> readFilterRecruitList(String industryType, String startAddress, String endAddress, Long minSalary, Long maxSalary, Pageable pageable) {
        Page<Recruit> recruitPage = recruitRepositoryCustom.findRecruitWithFilters(industryType, startAddress, endAddress, minSalary, maxSalary, pageable);
        return recruitPage.map(RecruitListResponseDto::toDto);
    }
}
