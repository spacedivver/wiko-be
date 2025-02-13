package TenMWon.wiko.resume.controller;

import TenMWon.wiko.User.repository.UserRepository;
import TenMWon.wiko.User.service.UserService;
import TenMWon.wiko.common.entity.BaseResponse;
import TenMWon.wiko.common.entity.BaseResponseStatus;
import TenMWon.wiko.common.exception.BaseException;
import TenMWon.wiko.recruit.dto.out.RecruitListResponseDto;
import TenMWon.wiko.resume.dto.in.ResumeRequestDto;
import TenMWon.wiko.resume.dto.out.ResumeListResponseDto;
import TenMWon.wiko.resume.service.ResumeService;
import TenMWon.wiko.resume.vo.in.ResumeRequestVo;
import TenMWon.wiko.resume.vo.out.ResumeResponseVo;
import TenMWon.wiko.security.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/resume")
@Slf4j
public class ResumeController {

    @Value("${jwt.secret}")
    private String secretKey;

    private final ResumeService resumeService;
    private final JwtTokenUtil jwtTokenUtil;

    @Operation(summary = "Resume 등록 API", description = "Resume 등록 API 입니다.", tags = {"Resume"})
    @PostMapping
    public BaseResponse<Void> createResume(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody ResumeRequestVo resumeRequestVo) {
        String token = null;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
        }
        String loginId = jwtTokenUtil.getLoginId(token, secretKey);
        resumeService.createResume(ResumeRequestDto.toDto(loginId, resumeRequestVo));
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "Resume 조회 API", description = " 이력서 내용을 조회하는 API 입니다.", tags = {"Resume"})
    @GetMapping("/read")
    public BaseResponse<Page<ResumeListResponseDto>> readResume(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        String token = authorization.replace("Bearer ", "");
        String loginId = jwtTokenUtil.getLoginId(token, secretKey);
        Page<ResumeListResponseDto> resumeListResponseVoList = resumeService.readResumeList(page, size, loginId);
        return new BaseResponse<>(resumeListResponseVoList);
    }

    @Operation(summary = "Resume 삭제 API", description = " 이력서를 삭제하는 API 입니다.", tags = {"Resume"})
    @DeleteMapping("/delete")
    public BaseResponse<Page<ResumeListResponseDto>> deleteResume(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestParam Long resumeId) {
        String token = authorization.replace("Bearer ", "");
        String loginId = jwtTokenUtil.getLoginId(token, secretKey);
        resumeService.deleteResume(resumeId);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }
}
