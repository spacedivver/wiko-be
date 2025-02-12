package TenMWon.wiko.resume.dto.out;

import TenMWon.wiko.resume.dto.in.CareerRequestDto;
import TenMWon.wiko.resume.entity.CareerType;
import TenMWon.wiko.resume.entity.Resume;
import TenMWon.wiko.resume.vo.out.ResumeResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class ResumeListResponseDto {

    private String resumeImage;
    private String education;
    private String languageSkill;
    private CareerType careerType;
    private CareerRequestDto careerDetail;
    private List<String> strength;
    private List<String> jobSkill;
    private String introduction;

    public static ResumeListResponseDto toDto(Resume resume, CareerRequestDto careerDetail) {
        return ResumeListResponseDto.builder()
                .resumeImage(resume.getResumeImage())
                .education(resume.getEducation())
                .languageSkill(resume.getLanguageSkill())
                .careerType(resume.getCareerType())
                .careerDetail(careerDetail)
                .strength(resume.getStrength())
                .jobSkill(resume.getJobSkill())
                .introduction(resume.getIntroduction())
                .build();
    }

    public ResumeResponseVo toVo() {
        return ResumeResponseVo.builder()
                .resumeImage(resumeImage)
                .education(education)
                .languageSkill(languageSkill)
                .careerType(careerType)
                .careerDetail(careerDetail)
                .strength(strength)
                .jobSkill(jobSkill)
                .introduction(introduction)
                .build();
    }
}
