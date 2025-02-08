package TenMWon.wiko.recruit.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IndustryType {

    외식_음료("외식-음료"),
    서비스("서비스"),
    유통_판매_영업("유통-판매-영업"),
    문화_여가_생활("문화-여가-생활"),
    고객상담_리서치("고객상담-리서치"),
    생산_건설_노무("생산-건설-노무"),
    통역_번역("통역-번역"),
    운전_배달("운전-배달"),
    교육_강사("교육-강사");

    private final String industryType;

    @JsonValue
    public String getIndustryType() {
        return industryType;
    }

    @JsonCreator
    public static IndustryType fromValue(String value) {
        for (IndustryType industryType : IndustryType.values()) {
            if (industryType.industryType.equals(value)) {
                return industryType;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
