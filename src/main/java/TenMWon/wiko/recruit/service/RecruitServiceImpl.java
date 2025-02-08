package TenMWon.wiko.recruit.service;

import TenMWon.wiko.common.entity.BaseResponseStatus;
import TenMWon.wiko.common.exception.BaseException;
import TenMWon.wiko.recruit.dto.in.RecruitRequestDto;
import TenMWon.wiko.recruit.dto.out.RecruitListResponseDto;
import TenMWon.wiko.recruit.dto.out.RecruitResponseDto;
import TenMWon.wiko.recruit.entity.Recruit;
import TenMWon.wiko.recruit.repository.RecruitRepository;
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
}
