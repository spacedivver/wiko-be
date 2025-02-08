package TenMWon.wiko.recruit.controller;

import TenMWon.wiko.common.entity.BaseResponse;
import TenMWon.wiko.common.entity.BaseResponseStatus;
import TenMWon.wiko.recruit.dto.in.RecruitRequestDto;
import TenMWon.wiko.recruit.dto.out.RecruitListResponseDto;
import TenMWon.wiko.recruit.service.RecruitService;
import TenMWon.wiko.recruit.vo.in.RecruitRequestVo;
import TenMWon.wiko.recruit.vo.out.RecruitListResponseVo;
import TenMWon.wiko.recruit.vo.out.RecruitResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/recruit")
public class RecruitController {

    private final RecruitService recruitService;

    @Operation(summary = "Recruit 등록 API", description = "Recruit 등록 API 입니다.", tags = {"Recruit"})
    @PostMapping
    public BaseResponse<Void> createRecruit(
            @RequestBody RecruitRequestVo recruitRequestVo) {
        recruitService.createRecruit(RecruitRequestDto.toDto(recruitRequestVo));
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "Recruit List API", description = "최신순으로 일자리 정보를 List로 조회하는 API 입니다.", tags = {"Recruit"})
    @GetMapping("/list")
    public BaseResponse<List<RecruitListResponseVo>> readRecruitList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<RecruitListResponseVo> recruitListResponseVoList = recruitService.readRecruitList(page, size)
                .stream()
                .map(RecruitListResponseDto::toVo)
                .toList();
        return new BaseResponse<>(recruitListResponseVoList);
    }

    @Operation(summary = "Recruit 필터링 조회 API", description = "업종, 지역, 급여 별 필터링 조회하는 API 입니다.", tags = {"Recruit"})
    @GetMapping("/filterList")
    public Page<RecruitListResponseDto> readFilterRecruitList(
            @RequestParam(required = false) String industryType,
            @RequestParam(required = false) String startAddress,
            @RequestParam(required = false) String endAddress,
            @RequestParam(required = false) Long minSalary,
            @RequestParam(required = false) Long maxSalary,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return recruitService.readFilterRecruitList(industryType, startAddress, endAddress,
                minSalary, maxSalary, pageable);
    }

    @Operation(summary = "Recruit 상세 조회 API", description = "recruitId로 일자리 정보의 상세 내용을 조회하는 API 입니다.", tags = {"Recruit"})
    @GetMapping("/detail")
    public BaseResponse<RecruitResponseVo> readRecruitDetail(@RequestParam Long recruitId) {
        return new BaseResponse<>(recruitService.readRecruitDetail(recruitId).toVo());
    }
}
