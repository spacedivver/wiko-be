package TenMWon.wiko.resume.dto.in;

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
    private String career;
    private List<String> strength;
    private List<String> jobSkill;
    private String introduction;

    @Builder
    public ResumeRequestDto(String resumeImage, String education, String languageSkill, String career,
                            List<String> strength, List<String> jobSkill, String introduction) {
        this.resumeImage = resumeImage;
        this.education = education;
        this.languageSkill = languageSkill;
        this.career = career;
        this.strength = strength;
        this.jobSkill = jobSkill;
        this.introduction = introduction;
    }

    public Resume toEntity() {
        return Resume.builder()
                .resumeImage(resumeImage)
                .education(education)
                .languageSkill(languageSkill)
                .career(career)
                .strength(strength)
                .jobSkill(jobSkill)
                .introduction(introduction)
                .build();
    }
    public static ResumeRequestDto toDto(ResumeRequestVo resumeRequestVo) {
        return ResumeRequestDto.builder()
                .resumeImage(resumeRequestVo.getResumeImage())
                .education(resumeRequestVo.getEducation())
                .languageSkill(resumeRequestVo.getLanguageSkill())
                .career(resumeRequestVo.getCareer())
                .strength(resumeRequestVo.getStrength())
                .jobSkill(resumeRequestVo.getJobSkill())
                .introduction(resumeRequestVo.getIntroduction())
                .build();
    }
}
