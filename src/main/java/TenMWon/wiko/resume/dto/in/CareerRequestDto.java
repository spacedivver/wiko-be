package TenMWon.wiko.resume.dto.in;

import TenMWon.wiko.resume.entity.Career;
import TenMWon.wiko.resume.entity.Resume;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CareerRequestDto {

    private String company;
    private Boolean isWorking;
    private String joinedAt;
    private String leavedAt;
    private String joinedAtMonth;
    private String position;

    @Builder
    public CareerRequestDto(String company, Boolean isWorking,
                            String joinedAt, String leavedAt, String joinedAtMonth, String position){
        this.company = company;
        this.isWorking = isWorking;
        this.joinedAt = joinedAt;
        this.leavedAt = leavedAt;
        this.joinedAtMonth = joinedAtMonth;
        this.position = position;
    }

    public Career toEntity(Resume resume) {
        return Career.builder()
                .company(company)
                .isWorking(isWorking)
                .joinedAt(joinedAt)
                .leavedAt(leavedAt)
                .joinedAtMonth(joinedAtMonth)
                .position(position)
                .resume(resume)
                .build();
    }

    public static CareerRequestDto toDto(Career career) {
        return CareerRequestDto.builder()
                .company(career.getCompany())
                .isWorking(career.getIsWorking())
                .joinedAt(career.getJoinedAt())
                .leavedAt(career.getLeavedAt())
                .joinedAtMonth(career.getJoinedAtMonth())
                .position(career.getPosition())
                .build();
    }
}
