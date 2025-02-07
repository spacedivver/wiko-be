package TenMWon.wiko.recruit.dto.out;

import TenMWon.wiko.recruit.entity.ContractType;
import TenMWon.wiko.recruit.entity.Recruit;
import TenMWon.wiko.recruit.entity.SalaryType;
import TenMWon.wiko.recruit.vo.out.RecruitResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class RecruitResponseDto {
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

    public static RecruitResponseDto toDto(Recruit recruit) {
        return RecruitResponseDto.builder()
                .title(recruit.getTitle())
                .salary(recruit.getSalary())
                .salaryType(recruit.getSalaryType())
                .contractType(recruit.getContractType())
                .workingPeriod(recruit.getWorkingPeriod())
                .workingHours(recruit.getWorkingHours())
                .deadline(recruit.getDeadline())
                .detail(recruit.getDetail())
                .companyName(recruit.getCompanyName())
                .companyAddress(recruit.getCompanyAddress())
                .industryType(recruit.getIndustryType())
                .contactName(recruit.getContactName())
                .contactPhone(recruit.getContactPhone())
                .contactEmail(recruit.getContactEmail())
                .build();
    }

    public RecruitResponseVo toVo() {
        return RecruitResponseVo.builder()
                .title(title)
                .salary(salary)
                .salaryType(salaryType)
                .contractType(contractType)
                .workingPeriod(workingPeriod)
                .workingHours(workingHours)
                .deadline(deadline)
                .detail(detail)
                .companyName(companyName)
                .companyAddress(companyAddress)
                .industryType(industryType)
                .contactName(contactName)
                .contactPhone(contactPhone)
                .contactEmail(contactEmail)
                .build();
    }
}
