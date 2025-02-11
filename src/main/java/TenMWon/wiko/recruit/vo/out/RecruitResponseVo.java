package TenMWon.wiko.recruit.vo.out;

import TenMWon.wiko.recruit.entity.ContractType;
import TenMWon.wiko.recruit.entity.SalaryType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class RecruitResponseVo {

    private String title;
    private Long salary;
    private SalaryType salaryType;
    private ContractType contractType;
    private String workingPeriod;
    private String workingHours;
    private String advantage;
//    private String deadline;
    private String detail;
    private String companyLogo;
    private String companyName;
    private String companyAddress;
//    private IndustryType industryType;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
}
