package TenMWon.wiko.email.service;

import TenMWon.wiko.email.request.EmailRequest;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;


@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${employerEmail}")
    private String employerEmail;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(EmailRequest emailRequest) {

        String subject = "아래 지원자가 귀사의 채용 공고에 지원하고자 합니다.";

        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("email", emailRequest.getEmail());
        thymeleafContext.setVariable("subject", subject);
        thymeleafContext.setVariable("name", emailRequest.getName());
        thymeleafContext.setVariable("nation", emailRequest.getNation());
        thymeleafContext.setVariable("phone", emailRequest.getPhone());
        thymeleafContext.setVariable("visa", emailRequest.getVisa());
        thymeleafContext.setVariable("education", emailRequest.getEducation());
        thymeleafContext.setVariable("langSkill", emailRequest.getLangSkill());
        thymeleafContext.setVariable("career", emailRequest.getCareer());
        thymeleafContext.setVariable("strength", emailRequest.getStrength());
        thymeleafContext.setVariable("skills", emailRequest.getSkills());
        thymeleafContext.setVariable("introduction", emailRequest.getIntroduction());


        String htmlBody = templateEngine.process("emailTemplate", thymeleafContext);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom("test@test111.com");             // 플랫폼의 공식 발신자
            helper.setTo(employerEmail);                    // 고용주 이메일 (수신자)
            helper.setSubject("구직 지원");
            helper.setText(htmlBody, true);            // 두 번째 파라미터가 true이면 HTML 형식으로 전송됨
            helper.setReplyTo(emailRequest.getEmail());     // 답장 시 구직자 이메일로 전달

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
