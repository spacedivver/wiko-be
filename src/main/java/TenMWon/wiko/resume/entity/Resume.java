package TenMWon.wiko.resume.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import TenMWon.wiko.User.domain.entity.User;

import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;
    @Column(nullable = false, length = 100)
    private String resumeImage;
    @Column(nullable = false, length = 100)
    private String education;
    @Column(nullable = false, length = 100)
    private String languageSkill;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CareerType careerType;
    @Column(nullable = false, length = 500)
    private List<String> strength;
    @Column(nullable = false, length = 500)
    private List<String> jobSkill;
    @Column(nullable = false, length = 1000)
    private String introduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Builder
    public Resume(Long resumeId, String resumeImage, String education, String languageSkill,
                  CareerType careerType, List<String> strength, List<String>jobSkill, String introduction, User user) {
        this.resumeId = resumeId;
        this.resumeImage = resumeImage;
        this.education = education;
        this.languageSkill = languageSkill;
        this.careerType = careerType;
        this.strength = strength;
        this.jobSkill = jobSkill;
        this.introduction = introduction;
        this.user = user;
    }
}
