package TenMWon.wiko.resume.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long careerId;
    @Column(length = 200)
    private String company;
    @Column(length = 100)
    private Boolean isWorking;
    @Column(length = 100)
    private String startAt;
    @Column(length = 100)
    private String endAt;
    @Column(length = 100)
    private String workMonth;
    @Column(length = 1000)
    private String task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resumeId", nullable = false)
    private Resume resume;

    @Builder
    public Career(Long careerId, String company, Boolean isWorking, String workMonth,
                  String startAt, String endAt, String task, Resume resume) {
        this.careerId = careerId;
        this.company = company;
        this.isWorking = isWorking;
        this.workMonth = workMonth;
        this.startAt = startAt;
        this.endAt = endAt;
        this.task = task;
        this.resume = resume;
    }
}