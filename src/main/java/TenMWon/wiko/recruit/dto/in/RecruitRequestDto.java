package TenMWon.wiko.recruit.dto.in;

import TenMWon.wiko.recruit.entity.ContractType;
import TenMWon.wiko.recruit.entity.IndustryType;
import TenMWon.wiko.recruit.entity.Recruit;
import TenMWon.wiko.recruit.entity.SalaryType;
import TenMWon.wiko.recruit.vo.in.RecruitRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RecruitRequestDto {

    private String title;
    private Long salary;
    private SalaryType salaryType;
    private ContractType contractType;
    private String workingPeriod;
    private String workingHours;
    private LocalDateTime deadline;
    private String detail;
    private String advantage;
    private String companyLogo;
    private String companyName;
    private String companyAddress;
    private IndustryType industryType;
    private String contactName;
    private String contactPhone;
    private String contactEmail;

    @Builder
    public RecruitRequestDto(String title, Long salary, SalaryType salaryType,
                   ContractType contractType, String workingPeriod,
                   String workingHours, LocalDateTime deadline, String detail,
                   String companyName, String companyAddress, IndustryType industryType,
                   String contactName, String contactPhone, String contactEmail, String advantage, String companyLogo) {
        this.title = title;
        this.salary = salary;
        this.salaryType = salaryType;
        this.contractType = contractType;
        this.workingPeriod = workingPeriod;
        this.workingHours = workingHours;
        this.deadline = deadline;
        this.detail = detail;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.industryType = industryType;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.advantage = advantage;
        this.companyLogo = companyLogo;
    }

    public Recruit toEntity() {
        return Recruit.builder()
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
                .advantage(advantage)
                .companyLogo(companyLogo)
                .build();
    }

    public static RecruitRequestDto toDto(RecruitRequestVo recruitRequestVo) {
        return RecruitRequestDto.builder()
                .title(recruitRequestVo.getTitle())
                .salary(recruitRequestVo.getSalary())
                .salaryType(recruitRequestVo.getSalaryType())
                .contractType(recruitRequestVo.getContractType())
                .workingPeriod(recruitRequestVo.getWorkingPeriod())
                .workingHours(recruitRequestVo.getWorkingHours())
                .deadline(recruitRequestVo.getDeadline())
                .detail(recruitRequestVo.getDetail())
                .companyName(recruitRequestVo.getCompanyName())
                .companyAddress(recruitRequestVo.getCompanyAddress())
                .industryType(recruitRequestVo.getIndustryType())
                .contactName(recruitRequestVo.getContactName())
                .contactPhone(recruitRequestVo.getContactPhone())
                .contactEmail(recruitRequestVo.getContactEmail())
                .advantage(recruitRequestVo.getAdvantage())
                .companyLogo(recruitRequestVo.getCompanyLogo())
                .build();
    }

}
