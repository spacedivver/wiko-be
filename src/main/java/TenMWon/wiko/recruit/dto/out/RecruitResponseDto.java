package TenMWon.wiko.recruit.dto.out;

import TenMWon.wiko.recruit.entity.EmploymentType;
import TenMWon.wiko.recruit.entity.Recruit;
import TenMWon.wiko.recruit.vo.out.RecruitResponseVo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class RecruitResponseDto {

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


    public static RecruitResponseDto toDto(Recruit recruit) {
        return RecruitResponseDto.builder()
                .imgUrl(recruit.getImgUrl())
                .company(recruit.getCompany())
                .location(recruit.getLocation())
                .companyInfo(recruit.getCompanyInfo())
                .responsibilities(recruit.getResponsibilities())
                .qualifications(recruit.getQualification())
                .payType(recruit.getPayType())
                .pay(recruit.getPay())
                .employmentType(recruit.getEmploymentType())
                .workPeriod(recruit.getWorkingPeriod())
                .workHours(recruit.getWorkingHours())
                .preferredQualifications(recruit.getPreferredQualifications())
                .owner(recruit.getOwner())
                .phone(recruit.getPhone())
                .email(recruit.getEmail())
                .build();
    }

    public RecruitResponseVo toVo() {
        return RecruitResponseVo.builder()
                .imgUrl(imgUrl)
                .company(company)
                .location(location)
                .companyInfo(companyInfo)
                .responsibilities(responsibilities)
                .qualifications(qualifications)
                .payType(payType)
                .pay(pay)
                .employmentType(employmentType)
                .workPeriod(workPeriod)
                .workHours(workHours)
                .preferredQualifications(preferredQualifications)
                .owner(owner)
                .phone(phone)
                .email(email)
                .build();
    }
}
