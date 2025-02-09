package TenMWon.wiko.resume.dto.in;

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
    private List<CareerRequestDto> careerDetail;
    private List<String> strength;
    private List<String> jobSkill;
    private String introduction;

    @Builder
    public ResumeRequestDto(String resumeImage, String education, String languageSkill, CareerType careerType,
                            List<String> strength, List<String> jobSkill, String introduction, List<CareerRequestDto> careerDetail) {
        this.resumeImage = resumeImage;
        this.education = education;
        this.languageSkill = languageSkill;
        this.careerType = careerType;
        this.strength = strength;
        this.jobSkill = jobSkill;
        this.introduction = introduction;
        this.careerDetail = careerDetail;
    }

    public Resume toEntity() {
        return Resume.builder()
                .resumeImage(resumeImage)
                .education(education)
                .languageSkill(languageSkill)
                .careerType(careerType)
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
                .careerType(resumeRequestVo.getCareerType())
                .strength(resumeRequestVo.getStrength())
                .jobSkill(resumeRequestVo.getJobSkill())
                .introduction(resumeRequestVo.getIntroduction())
                .careerDetail(resumeRequestVo.getCareerDetail())
                .build();
    }
}
