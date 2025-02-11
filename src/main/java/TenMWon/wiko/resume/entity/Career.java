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
    private String joinedAt;
    @Column(length = 100)
    private String leavedAt;
    @Column(length = 100)
    private String joinedAtMonth;
    @Column(length = 1000)
    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resumeId", nullable = false)
    private Resume resume;

    @Builder
    public Career(Long careerId, String company, Boolean isWorking, String joinedAtMonth,
                  String joinedAt, String leavedAt, String position, Resume resume) {
        this.careerId = careerId;
        this.company = company;
        this.isWorking = isWorking;
        this.joinedAtMonth = joinedAtMonth;
        this.joinedAt = joinedAt;
        this.leavedAt = leavedAt;
        this.position = position;
        this.resume = resume;
    }
}