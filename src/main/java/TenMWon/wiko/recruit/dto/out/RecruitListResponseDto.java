package TenMWon.wiko.recruit.dto.out;

import TenMWon.wiko.recruit.entity.Recruit;
import TenMWon.wiko.recruit.vo.out.RecruitListResponseVo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecruitListResponseDto {

    private Long id;
    private String company;
    private String title;
    private String location;
    private String payType;
    private Long pay;
    private String imgUrl;

    @Builder
    public RecruitListResponseDto(Long id, String company, String payType, String title, String location, Long pay, String imgUrl) {
        this.id = id;
        this.company = company;
        this.title = title;
        this.location = location;
        this.payType = payType;
        this.pay = pay;
        this.imgUrl = imgUrl;
    }

    public static RecruitListResponseDto toDto(Recruit recruit) {
        return RecruitListResponseDto.builder()
                .id(recruit.getRecruitId())
                .company(recruit.getCompany())
                .title(recruit.getTitle())
                .location(recruit.getLocation())
                .payType(recruit.getPayType())
                .pay(recruit.getPay())
                .imgUrl(recruit.getImgUrl())
                .build();
    }

    public RecruitListResponseVo toVo() {
        return RecruitListResponseVo.builder()
                .id(id)
                .title(title)
                .company(company)
                .location(location)
                .payType(payType)
                .pay(pay)
                .imgUrl(imgUrl)
                .build();
    }
}
