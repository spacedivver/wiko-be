package TenMWon.wiko.recruit.dto.out;

import TenMWon.wiko.recruit.entity.Recruit;
import TenMWon.wiko.recruit.entity.SalaryType;
import TenMWon.wiko.recruit.vo.out.RecruitListResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecruitListResponseDto {

    private String title;
    private String companyLogo;
    private String companyName;
    private String companyAddress;
    private SalaryType salaryType;
    private Long salary;

    @Builder
    public RecruitListResponseDto(String title, String companyLogo, String companyName, String companyAddress, SalaryType salaryType, Long salary) {
        this.title = title;
        this.companyLogo = companyLogo;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.salaryType = salaryType;
        this.salary = salary;
    }

    public static RecruitListResponseDto toDto(Recruit recruit) {
        return RecruitListResponseDto.builder()
                .title(recruit.getTitle())
                .companyLogo(recruit.getCompanyLogo())
                .companyName(recruit.getCompanyName())
                .companyAddress(recruit.getCompanyAddress())
                .salaryType(recruit.getSalaryType())
                .salary(recruit.getSalary())
                .build();
    }

    public RecruitListResponseVo toVo() {
        return RecruitListResponseVo.builder()
                .title(title)
                .companyLogo(companyLogo)
                .companyName(companyName)
                .companyAddress(companyAddress)
                .salaryType(salaryType)
                .salary(salary)
                .build();
    }
}
