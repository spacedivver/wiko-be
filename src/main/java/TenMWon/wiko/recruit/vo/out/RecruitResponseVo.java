package TenMWon.wiko.recruit.vo.out;

import TenMWon.wiko.recruit.entity.ContractType;
import TenMWon.wiko.recruit.entity.Recruit;
import TenMWon.wiko.recruit.entity.SalaryType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;

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
    private LocalDateTime deadline;
    private String detail;
    private String companyName;
    private String companyAddress;
    private String industryType;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
}
