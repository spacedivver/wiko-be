package TenMWon.wiko.recruit.dto.out;

import TenMWon.wiko.recruit.entity.Recruit;
import TenMWon.wiko.recruit.vo.out.RecruitListResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class RecruitListResponseDto {

    private String title;
    private String companyName;
    private String companyAddress;
    private Long salary;

    @Builder
    public RecruitListResponseDto(String title, String companyName, String companyAddress, Long salary) {
        this.title = title;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.salary = salary;
    }

    public static RecruitListResponseDto toDto(Recruit recruit) {
        return RecruitListResponseDto.builder()
                .title(recruit.getTitle())
                .companyName(recruit.getCompanyName())
                .companyAddress(recruit.getCompanyAddress())
                .salary(recruit.getSalary())
                .build();
    }

    public RecruitListResponseVo toVo() {
        return RecruitListResponseVo.builder()
                .title(title)
                .companyName(companyName)
                .companyAddress(companyAddress)
                .salary(salary)
                .build();
    }
}
