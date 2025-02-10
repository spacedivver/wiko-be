package TenMWon.wiko.resume.dto.in;

import TenMWon.wiko.User.domain.entity.User;
import TenMWon.wiko.resume.entity.CareerType;
import TenMWon.wiko.resume.entity.Resume;
import TenMWon.wiko.resume.vo.in.ResumeRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResumeRequestDto {

    private String resumeImage;
    private String education;
    private String languageSkill;
    private CareerType careerType;
    private CareerRequestDto careerDetail;
    private List<String> strength;
    private List<String> jobSkill;
    private String introduction;
    private Long userId;

    @Builder
    public ResumeRequestDto(Long userId, String resumeImage, String education, String languageSkill, CareerType careerType,
                            List<String> strength, List<String> jobSkill, String introduction, CareerRequestDto careerDetail) {
        this.resumeImage = resumeImage;
        this.education = education;
        this.languageSkill = languageSkill;
        this.careerType = careerType;
        this.strength = strength;
        this.jobSkill = jobSkill;
        this.introduction = introduction;
        this.careerDetail = careerDetail;
        this.userId = userId;
    }

    public Resume toEntity(User user) {
        return Resume.builder()
                .resumeImage(resumeImage)
                .education(education)
                .languageSkill(languageSkill)
                .careerType(careerType)
                .strength(strength)
                .jobSkill(jobSkill)
                .introduction(introduction)
                .user(user)
                .build();
    }
    public static ResumeRequestDto toDto(Long userId, ResumeRequestVo resumeRequestVo) {
        return ResumeRequestDto.builder()
                .resumeImage(resumeRequestVo.getResumeImage())
                .education(resumeRequestVo.getEducation())
                .languageSkill(resumeRequestVo.getLanguageSkill())
                .careerType(resumeRequestVo.getCareerType())
                .careerDetail(resumeRequestVo.getCareerDetail())
                .strength(resumeRequestVo.getStrength())
                .jobSkill(resumeRequestVo.getJobSkill())
                .introduction(resumeRequestVo.getIntroduction())
                .userId(userId)
                .build();
    }
}
