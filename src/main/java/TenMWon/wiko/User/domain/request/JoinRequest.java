package TenMWon.wiko.User.domain.request;

import TenMWon.wiko.User.domain.entity.User;
import TenMWon.wiko.User.domain.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {

    @NotBlank(message = "이름이 비어있습니다.")
    private String name;

    @NotBlank(message = "로그인 아이디가 비어있습니다.")
    private String loginId;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String password;
    private String passwordCheck;

    private String address;
    private String visaType;


    public User toEntity(String encodedPassword) {
        return User.builder()
                .name(this.name)
                .loginId(this.loginId)
                .password(encodedPassword)
                .address(this.address)
                .visaType(this.visaType)
                .role(UserRole.USER)
                .build();
    }
}
