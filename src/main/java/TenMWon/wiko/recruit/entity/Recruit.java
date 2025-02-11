package TenMWon.wiko.recruit.entity;

import TenMWon.wiko.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Recruit extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruitId;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = false, length = 100)
    private Long salary;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SalaryType salaryType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractType contractType;
    @Column(nullable = false)
    private String workingPeriod;
    @Column(nullable = false, length = 100)
    private String workingHours;
    @Column(nullable = false)
    private String deadline;
    @Column(nullable = false, length  = 1000)
    private String detail;
    @Column(nullable = false, length = 100)
    private String advantage;

    @Column(nullable = false, length = 100)
    private String companyLogo;
    @Column(nullable = false, length = 100)
    private String companyName;
    @Column(nullable = false, length = 500)
    private String companyAddress;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IndustryType industryType;

    @Column(nullable = false, length = 50)
    private String contactName;
    @Column(nullable = false, length = 20)
    private String contactPhone;
    @Column(nullable = false, length = 100)
    private String contactEmail;

    @Builder
    public Recruit(Long recruitId, String title, Long salary, SalaryType salaryType,
                   ContractType contractType, String workingPeriod,
                   String workingHours, String deadline, String detail,
                   String companyName, String companyAddress, IndustryType industryType,
                   String contactName, String contactPhone, String contactEmail, String advantage, String companyLogo) {
        this.recruitId = recruitId;
        this.title = title;
        this.salary = salary;
        this.salaryType = salaryType;
        this.contractType = contractType;
        this.workingPeriod = workingPeriod;
        this.workingHours = workingHours;
        this.deadline = deadline;
        this.detail = detail;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.industryType = industryType;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.advantage = advantage;
        this.companyLogo = companyLogo;
    }
}
