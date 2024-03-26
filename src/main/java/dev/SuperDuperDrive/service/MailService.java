package dev.SuperDuperDrive.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private JavaMailSender mailSender;
    private TokenService tokenService;

    public MailService(JavaMailSender mailSender, TokenService tokenService) {
        this.mailSender = mailSender;
        this.tokenService = tokenService;
    }

    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendMail(String toEmail) throws Exception {
        String token = tokenService.generateToken(toEmail);
        String subject = "Reset your password";
        String html = "<p>We received a request to change your password on SuperDuperDrive.</p>" +
                "<p>Click <a href=\"http://localhost:8080/resetpass/" + token
                + "\">here</a> to change your password. This link is valid for three hours.</p>" +
                "<p>If you didn’t request a password change, you can ignore this message and continue to use your current password.</p>";

        MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
        helper.setFrom(fromMail, "SuperDuperDrive");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(html, true);

        mailSender.send(helper.getMimeMessage());
    }

}
