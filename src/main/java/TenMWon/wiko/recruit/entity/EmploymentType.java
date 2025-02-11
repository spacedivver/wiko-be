package TenMWon.wiko.recruit.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmploymentType {

    정규직("정규직"),
    계약직("계약직"),
    파견직("파견직");

    private final String employmentType;

    @JsonValue
    public String getContractType() {
        return employmentType;
    }

    @JsonCreator
    public static EmploymentType fromValue(String value) {
        for (EmploymentType employmentType : EmploymentType.values()) {
            if (employmentType.employmentType.equals(value)) {
                return employmentType;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
