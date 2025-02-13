package TenMWon.wiko.email.request;

import lombok.Data;

@Data
public class EmailRequest {
    // 고용주 이메일 주소 (수신자)
//    private String employerEmail;

    // content 영역
    private String name;
    private String nation;
    private String phone;
    private String visa;
    private String education;
    private String langSkill;
    private String career;
    private String[] strength;
    private String[] skills;
    private String introduction;

    // 구직자 이메일 주소
    private String email;
}

