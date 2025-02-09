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
    private String startAt;
    private String endAt;
    private String workMonth;
    private String task;

    @Builder
    public CareerRequestDto(String company, Boolean isWorking,
                            String startAt, String endAt, String workMonth, String task){
        this.company = company;
        this.isWorking = isWorking;
        this.startAt = startAt;
        this.endAt = endAt;
        this.workMonth = workMonth;
        this.task = task;
    }

    public Career toEntity(Resume resume) {
        return Career.builder()
                .company(company)
                .isWorking(isWorking)
                .startAt(startAt)
                .endAt(endAt)
                .workMonth(workMonth)
                .task(task)
                .resume(resume)
                .build();
    }
}
