package TenMWon.wiko.resume.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CareerType {

    신입("신입"),
    경력("경력");

    private final String careerType;

    @JsonValue
    public String getCareerType() {
        return careerType;
    }

    @JsonCreator
    public static CareerType fromValue(String value) {
        for (CareerType careerType : CareerType.values()) {
            if (careerType.careerType.equals(value)) {
                return careerType;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
