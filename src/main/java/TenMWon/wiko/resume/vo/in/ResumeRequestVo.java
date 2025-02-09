package TenMWon.wiko.resume.vo.in;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ResumeRequestVo {

    private String resumeImage;
    private String education;
    private String languageSkill;
    private String career;
    private List<String> strength;
    private List<String> jobSkill;
    private String introduction;

}
