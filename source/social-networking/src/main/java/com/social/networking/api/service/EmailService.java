package com.social.networking.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Map;

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
            message.setContent(thymeleafService.createContentGetOTP("email.html", variables), "text/html; charset=utf-8");
            emailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
