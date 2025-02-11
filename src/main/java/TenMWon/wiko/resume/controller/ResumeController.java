package TenMWon.wiko.resume.controller;

import TenMWon.wiko.common.entity.BaseResponse;
import TenMWon.wiko.common.entity.BaseResponseStatus;
import TenMWon.wiko.resume.dto.in.ResumeRequestDto;
import TenMWon.wiko.resume.service.ResumeService;
import TenMWon.wiko.resume.vo.in.ResumeRequestVo;
import TenMWon.wiko.resume.vo.out.ResumeResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/resume")
public class ResumeController {

    private final ResumeService resumeService;

    @Operation(summary = "Resume 등록 API", description = "Resume 등록 API 입니다.", tags = {"Resume"})
    @PostMapping
    public BaseResponse<Void> createResume(
            @RequestHeader("userId") Long userId,
            @RequestBody ResumeRequestVo resumeRequestVo) {
        resumeService.createResume(ResumeRequestDto.toDto(userId, resumeRequestVo));
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "Resume 조회 API", description = "userId로 이력서 내용을 조회하는 API 입니다.", tags = {"Resume"})
    @GetMapping("/read")
    public BaseResponse<ResumeResponseVo> readResume(@RequestParam Long userId) {
        return new BaseResponse<>(resumeService.readResume(userId).toVo());
    }
}
