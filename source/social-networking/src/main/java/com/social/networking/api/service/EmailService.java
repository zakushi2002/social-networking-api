package com.social.networking.api.service;

import com.social.networking.api.constant.SocialNetworkingConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private ThymeleafService thymeleafService;

    public void sendEmail(String email, Map<String, Object> variables, String subject) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject(subject);
            String template = "email.html";
            if (Objects.equals(subject, SocialNetworkingConstant.OTP_SUBJECT_EMAIL)) {
                message.setContent(thymeleafService.createContent(template, variables), "text/html; charset=utf-8");
            } else {
                template = "course.html";
                message.setContent(thymeleafService.createContent(template, variables), "text/html; charset=utf-8");
            }
            emailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
