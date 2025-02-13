package TenMWon.wiko.recruit.vo.out;

import TenMWon.wiko.recruit.entity.EmploymentType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class RecruitResponseVo {

    private String imgUrl;
    private String company;
    private String location;
    private String companyInfo;
    private String responsibilities;
    private String qualifications;
    private String payType;
    private String pay;
    private EmploymentType employmentType;
    private String workPeriod;
    private String workHours;
    private String preferredQualifications;
    private String owner;
    private String phone;
    private String email;

}
