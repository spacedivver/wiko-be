package TenMWon.wiko.recruit.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IndustryType {

    OUTDOOR_DRINK("외식·음료"),
    SERVICE("서비스"),
    RETAIL_SALES("유통·판매·영업"),
    CULTURE_LEISURE("문화·여가·생활"),
    CUSTOMER_SERVICE_RESEARCH("고객상담·리서치"),
    PRODUCTION_CONSTRUCTION("생산·건설·노무"),
    TRANSLATION("통역·번역"),
    DELIVERY_DRIVER("운전·배달"),
    EDUCATION_INSTRUCTOR("교육·강사");

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
