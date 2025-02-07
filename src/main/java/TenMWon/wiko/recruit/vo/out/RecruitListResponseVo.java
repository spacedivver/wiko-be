package TenMWon.wiko.recruit.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class RecruitListResponseVo {

    private String title;
    private String companyName;
    private String companyAddress;
    private Long salary;
}
