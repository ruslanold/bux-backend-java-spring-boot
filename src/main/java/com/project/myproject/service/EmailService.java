package com.project.myproject.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Slf4j
@Service
@AllArgsConstructor
public class EmailService implements IEmailService{

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String msg) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(msg, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("bestXmail@amigoscode.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }
}
