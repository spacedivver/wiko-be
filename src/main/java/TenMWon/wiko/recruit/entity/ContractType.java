package TenMWon.wiko.recruit.entity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContractType {

    정규직("정규직"),
    계약직("계약직"),
    파견직("파견직");

    private final String contractType;

    @JsonValue
    public String getContractType() {
        return contractType;
    }

    @JsonCreator
    public static ContractType fromValue(String value) {
        for (ContractType contractType : ContractType.values()) {
            if (contractType.contractType.equals(value)) {
                return contractType;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
