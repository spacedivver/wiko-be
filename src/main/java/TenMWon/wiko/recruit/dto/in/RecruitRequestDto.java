package TenMWon.wiko.recruit.dto.in;

import TenMWon.wiko.recruit.entity.EmploymentType;
import TenMWon.wiko.recruit.entity.IndustryType;
import TenMWon.wiko.recruit.entity.Recruit;
import TenMWon.wiko.recruit.vo.in.RecruitRequestVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecruitRequestDto {

    private String title;
    private String pay;
    private EmploymentType employmentType;
    private String workingPeriod;
    private String workingHours;
    private String deadline;
    private String qualification;
    private String responsibilities;
    private String preferredQualifications;
    private String imgUrl;
    private String company;
    private String jobName;
    private String companyInfo;
    private String location;
    private IndustryType industryType;

    @Builder
    public RecruitRequestDto(String title, String pay,
                   EmploymentType employmentType, String workingPeriod,
                   String workingHours, String deadline, String qualification,
                   String responsibilities, String jobName, String company, String imgUrl, String companyInfo, String location, IndustryType industryType, String preferredQualifications) {
        this.title = title;
        this.pay = pay;
        this.employmentType = employmentType;
        this.workingPeriod = workingPeriod;
        this.workingHours = workingHours;
        this.deadline = deadline;
        this.qualification = qualification;
        this.responsibilities = responsibilities;
        this.imgUrl = imgUrl;
        this.jobName = jobName;
        this.company = company;
        this.location = location;
        this.industryType = industryType;
        this.companyInfo = companyInfo;
        this.preferredQualifications = preferredQualifications;
    }

    public Recruit toEntity() {
        return Recruit.builder()
                .title(title)
                .pay(pay)
                .employmentType(employmentType)
                .workingPeriod(workingPeriod)
                .workingHours(workingHours)
                .deadline(deadline)
                .deadline(deadline)
                .qualification(qualification)
                .responsibilities(responsibilities)
                .preferredQualifications(preferredQualifications)
                .imgUrl(imgUrl)
                .company(company)
                .jobName(jobName)
                .companyInfo(companyInfo)
                .location(location)
                .industryType(industryType)
                .build();
    }

    public static RecruitRequestDto toDto(RecruitRequestVo recruitRequestVo) {
        return RecruitRequestDto.builder()
                .title(recruitRequestVo.getTitle())
                .pay(recruitRequestVo.getPay())
                .employmentType(recruitRequestVo.getEmploymentType())
                .workingPeriod(recruitRequestVo.getWorkingPeriod())
                .workingHours(recruitRequestVo.getWorkingHours())
                .deadline(recruitRequestVo.getDeadline())
                .qualification(recruitRequestVo.getQualification())
                .responsibilities(recruitRequestVo.getResponsibilities())
                .preferredQualifications(recruitRequestVo.getPreferredQualifications())
                .imgUrl(recruitRequestVo.getImgUrl())
                .company(recruitRequestVo.getCompany())
                .jobName(recruitRequestVo.getJobName())
                .companyInfo(recruitRequestVo.getCompanyInfo())
                .location(recruitRequestVo.getLocation())
                .industryType(recruitRequestVo.getIndustryType())
                .build();
    }


}
