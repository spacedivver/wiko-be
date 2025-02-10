package TenMWon.wiko.resume.vo.out;

import TenMWon.wiko.resume.dto.in.CareerRequestDto;
import TenMWon.wiko.resume.entity.CareerType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class ResumeResponseVo {

    private String resumeImage;
    private String education;
    private String languageSkill;
    private CareerType careerType;
    private List<CareerRequestDto> careerDetail;
    private List<String> strength;
    private List<String> jobSkill;
    private String introduction;

}
