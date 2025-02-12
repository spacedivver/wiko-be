package TenMWon.wiko.resume.controller;

import TenMWon.wiko.User.repository.UserRepository;
import TenMWon.wiko.User.service.UserService;
import TenMWon.wiko.common.entity.BaseResponse;
import TenMWon.wiko.common.entity.BaseResponseStatus;
import TenMWon.wiko.common.exception.BaseException;
import TenMWon.wiko.resume.dto.in.ResumeRequestDto;
import TenMWon.wiko.resume.service.ResumeService;
import TenMWon.wiko.resume.vo.in.ResumeRequestVo;
import TenMWon.wiko.resume.vo.out.ResumeResponseVo;
import TenMWon.wiko.security.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/resume")
@Slf4j
public class ResumeController {

    private final ResumeService resumeService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Operation(summary = "Resume 등록 API", description = "Resume 등록 API 입니다.", tags = {"Resume"})
    @PostMapping
    public BaseResponse<Void> createResume(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody ResumeRequestVo resumeRequestVo) {
        String token = authorization.replace("Bearer ", "");
        String loginId = jwtTokenUtil.getLoginId(token, "my-secret-key-123123");
        resumeService.createResume(ResumeRequestDto.toDto(loginId, resumeRequestVo));
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "Resume 조회 API", description = " 이력서 내용을 조회하는 API 입니다.", tags = {"Resume"})
    @GetMapping("/read")
    public BaseResponse<ResumeResponseVo> readResume(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        String token = authorization.replace("Bearer ", "");
        String loginId = jwtTokenUtil.getLoginId(token, "my-secret-key-123123");
        ResumeResponseVo resumeResponseVo = resumeService.readResume(loginId).toVo();
        return new BaseResponse<>(resumeResponseVo);
    }
}
