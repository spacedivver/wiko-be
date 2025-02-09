package TenMWon.wiko.recruit.vo.out;

import TenMWon.wiko.recruit.entity.SalaryType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class RecruitListResponseVo {

    private String title;
    private String companyLogo;
    private String companyName;
    private String companyAddress;
    private SalaryType salaryType;
    private Long salary;
}
